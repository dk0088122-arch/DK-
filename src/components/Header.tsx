import React from 'react';
import { AppConfig, AppScreen } from '../types';
import { Shield, Lock, ArrowLeft, Gem } from 'lucide-react';

interface HeaderProps {
  currentScreen: AppScreen;
  appConfig: AppConfig;
  onNavigate: (screen: AppScreen) => void;
  isAdminLoggedIn?: boolean;
}

export const Header: React.FC<HeaderProps> = ({
  currentScreen,
  appConfig,
  onNavigate,
  isAdminLoggedIn
}) => {
  return (
    <header className="sticky top-0 z-40 bg-white/95 backdrop-blur-md border-b border-[#E0E3E8] shadow-sm">
      <div className="max-w-4xl mx-auto px-4 py-3 flex items-center justify-between">
        <div className="flex items-center space-x-3">
          {currentScreen !== 'HOME' && (
            <button
              onClick={() => onNavigate('HOME')}
              className="p-2 -ml-1 text-[#0061A4] hover:bg-[#D1E4FF]/40 rounded-full transition-smooth"
              aria-label="Back to Home"
            >
              <ArrowLeft className="w-5 h-5" />
            </button>
          )}

          <div
            onClick={() => onNavigate('HOME')}
            className="flex items-center space-x-2.5 cursor-pointer select-none"
          >
            <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-[#00487D] to-[#0061A4] flex items-center justify-center shadow-md shadow-[#0061A4]/20 text-white">
              <Shield className="w-6 h-6 fill-white/20" />
            </div>
            <div>
              <h1 className="text-base font-black tracking-tight text-[#002E54] leading-tight">
                {appConfig.appTitle || "FREE WARRIOR GIVEAWAY"}
              </h1>
              <p className="text-[11px] font-semibold text-[#72777F] tracking-wide flex items-center gap-1">
                <Gem className="w-3 h-3 text-[#FFB703] fill-[#FFB703]" />
                {appConfig.appSubtitle || "20,000 DIAMONDS GIVEAWAY"}
              </p>
            </div>
          </div>
        </div>

        <div className="flex items-center space-x-2">
          {currentScreen === 'ADMIN_DASHBOARD' ? (
            <span className="text-xs font-bold bg-[#D1E4FF] text-[#002E54] px-2.5 py-1 rounded-full border border-[#B0C9E8]">
              Admin Panel
            </span>
          ) : (
            <button
              onClick={() => onNavigate(isAdminLoggedIn ? 'ADMIN_DASHBOARD' : 'ADMIN_LOGIN')}
              className="flex items-center space-x-1.5 px-3 py-1.5 rounded-full text-xs font-bold text-[#0061A4] bg-[#F6F9FD] border border-[#B0C9E8] hover:bg-[#D1E4FF] transition-smooth"
            >
              <Lock className="w-3.5 h-3.5" />
              <span>Admin</span>
            </button>
          )}
        </div>
      </div>
    </header>
  );
};
