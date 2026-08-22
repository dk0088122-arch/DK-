import React, { useState } from 'react';
import { AppConfig, GiveawaySubmission, AdminMedia, AdminTab } from '../types';
import {
  Shield, Check, X, RefreshCw, Trash2, Plus, LogOut, Copy,
  Search, Filter, Sliders, Image, Eye, Save, RotateCcw,
  CheckCircle2, Clock, Sparkles, AlertCircle, Gem, Users,
  Mailbox, KeyRound, ExternalLink, HelpCircle
} from 'lucide-react';

interface AdminDashboardScreenProps {
  appConfig: AppConfig;
  submissions: GiveawaySubmission[];
  mediaList: AdminMedia[];
  onUpdateStatus: (id: string, status: 'PENDING' | 'APPROVED' | 'REJECTED') => void;
  onRegenerateOtp: (id: string) => void;
  onDeleteSubmission: (id: string) => void;
  onSaveConfig: (config: AppConfig) => void;
  onAddMedia: (media: Omit<AdminMedia, 'id' | 'createdAt'>) => void;
  onDeleteMedia: (id: string) => void;
  onClearAllMedia: () => void;
  onLogout: () => void;
}

export const AdminDashboardScreen: React.FC<AdminDashboardScreenProps> = ({
  appConfig,
  submissions,
  mediaList,
  onUpdateStatus,
  onRegenerateOtp,
  onDeleteSubmission,
  onSaveConfig,
  onAddMedia,
  onDeleteMedia,
  onClearAllMedia,
  onLogout
}) => {
  const [activeTab, setActiveTab] = useState<AdminTab>('SUBMISSIONS');
  const [searchQuery, setSearchQuery] = useState('');
  const [statusFilter, setStatusFilter] = useState<'ALL' | 'PENDING' | 'APPROVED' | 'REJECTED'>('ALL');
  const [copiedId, setCopiedId] = useState<string | null>(null);

  // Banner Customizer State
  const [appTitle, setAppTitle] = useState(appConfig.appTitle);
  const [appSubtitle, setAppSubtitle] = useState(appConfig.appSubtitle);
  const [bannerTitle, setBannerTitle] = useState(appConfig.bannerTitle);
  const [bannerSubtitle, setBannerSubtitle] = useState(appConfig.bannerSubtitle);
  const [bannerBadge, setBannerBadge] = useState(appConfig.bannerBadge);
  const [bannerImageUrl, setBannerImageUrl] = useState(appConfig.bannerImageUrl);
  const [isBannerActive, setIsBannerActive] = useState(appConfig.isBannerActive);
  const [bannerColorHex, setBannerColorHex] = useState(appConfig.bannerColorHex || '#0061A4');
  const [configSavedToast, setConfigSavedToast] = useState(false);

  // Media Form State
  const [mediaTitle, setMediaTitle] = useState('');
  const [mediaDesc, setMediaDesc] = useState('');
  const [mediaUrl, setMediaUrl] = useState('');
  const [mediaType, setMediaType] = useState<'IMAGE' | 'VIDEO'>('IMAGE');
  const [showMediaModal, setShowMediaModal] = useState(false);

  // Filtered Submissions
  const filteredSubmissions = submissions.filter((sub) => {
    const matchesSearch =
      sub.playerName.toLowerCase().includes(searchQuery.toLowerCase()) ||
      sub.euid.includes(searchQuery) ||
      sub.otpCode.includes(searchQuery);

    const matchesStatus = statusFilter === 'ALL' || sub.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  const pendingSubmissions = submissions.filter((s) => s.status === 'PENDING');
  const approvedCount = submissions.filter((s) => s.status === 'APPROVED').length;
  const totalDiamonds = approvedCount * 20000;

  const handleCopyCode = (id: string, code: string) => {
    navigator.clipboard.writeText(code);
    setCopiedId(id);
    setTimeout(() => setCopiedId(null), 2000);
  };

  const handleSaveBannerConfig = (e: React.FormEvent) => {
    e.preventDefault();
    const updated: AppConfig = {
      appTitle: appTitle.trim() || "FREE WARRIOR GIVEAWAY",
      appSubtitle: appSubtitle.trim() || "20,000 DIAMONDS GIVEAWAY",
      bannerTitle: bannerTitle.trim() || "DIAMOND AIRDROP & REDEEM EVENT",
      bannerSubtitle: bannerSubtitle.trim() || "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
      bannerBadge: bannerBadge.trim() || "OFFICIAL BOUNTY",
      bannerImageUrl: bannerImageUrl.trim(),
      isBannerActive,
      bannerColorHex
    };
    onSaveConfig(updated);
    setConfigSavedToast(true);
    setTimeout(() => setConfigSavedToast(false), 2500);
  };

  const handleAddMediaSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!mediaTitle.trim() || !mediaUrl.trim()) return;

    onAddMedia({
      title: mediaTitle.trim(),
      description: mediaDesc.trim(),
      url: mediaUrl.trim(),
      type: mediaType
    });

    setMediaTitle('');
    setMediaDesc('');
    setMediaUrl('');
    setShowMediaModal(false);
  };

  return (
    <div className="space-y-6 pb-16 max-w-4xl mx-auto">
      {/* Admin Top Header & Stats */}
      <div className="bg-white rounded-3xl p-6 border border-[#E0E3E8] shadow-sm space-y-6">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div className="flex items-center space-x-3">
            <div className="w-12 h-12 rounded-2xl bg-[#0061A4] text-white flex items-center justify-center shadow-md shadow-[#0061A4]/30">
              <Shield className="w-6 h-6" />
            </div>
            <div>
              <h2 className="text-xl font-black text-[#002E54]">
                Administration Console
              </h2>
              <p className="text-xs text-[#72777F] font-medium">
                Logged in as <span className="font-bold text-[#0061A4]">dkadmin</span> (Super Admin)
              </p>
            </div>
          </div>

          <button
            onClick={onLogout}
            className="inline-flex items-center space-x-2 px-4 py-2 rounded-full border border-red-200 text-red-600 hover:bg-red-50 text-xs font-bold transition-smooth"
          >
            <LogOut className="w-4 h-4" />
            <span>Sign Out</span>
          </button>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3.5">
          <div className="p-3.5 rounded-2xl bg-[#F6F9FD] border border-[#E0E3E8]">
            <div className="text-xs font-bold text-[#72777F] flex items-center gap-1">
              <Users className="w-3.5 h-3.5" /> Total Claims
            </div>
            <div className="text-2xl font-black text-[#002E54] mt-1">
              {submissions.length}
            </div>
          </div>

          <div className="p-3.5 rounded-2xl bg-amber-50 border border-amber-200">
            <div className="text-xs font-bold text-amber-800 flex items-center gap-1">
              <Clock className="w-3.5 h-3.5" /> Pending OTPs
            </div>
            <div className="text-2xl font-black text-amber-900 mt-1">
              {pendingSubmissions.length}
            </div>
          </div>

          <div className="p-3.5 rounded-2xl bg-emerald-50 border border-emerald-200">
            <div className="text-xs font-bold text-emerald-800 flex items-center gap-1">
              <CheckCircle2 className="w-3.5 h-3.5" /> Approved
            </div>
            <div className="text-2xl font-black text-emerald-900 mt-1">
              {approvedCount}
            </div>
          </div>

          <div className="p-3.5 rounded-2xl bg-[#D1E4FF] border border-[#B0C9E8]">
            <div className="text-xs font-bold text-[#002E54] flex items-center gap-1">
              <Gem className="w-3.5 h-3.5 text-[#FB8500]" /> Dispatched
            </div>
            <div className="text-2xl font-black text-[#002E54] mt-1 font-mono">
              {totalDiamonds.toLocaleString()}
            </div>
          </div>
        </div>
      </div>

      {/* Tabs Navigation */}
      <div className="flex border-b border-[#E0E3E8] bg-white rounded-2xl p-1 shadow-sm overflow-x-auto">
        <button
          onClick={() => setActiveTab('SUBMISSIONS')}
          className={`flex-1 py-3 px-4 rounded-xl text-xs md:text-sm font-bold flex items-center justify-center space-x-2 whitespace-nowrap transition-smooth ${
            activeTab === 'SUBMISSIONS'
              ? 'bg-[#0061A4] text-white shadow-md shadow-[#0061A4]/20'
              : 'text-[#42474E] hover:bg-[#F6F9FD]'
          }`}
        >
          <Users className="w-4 h-4" />
          <span>Submissions ({submissions.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('OTP_APPROVAL')}
          className={`flex-1 py-3 px-4 rounded-xl text-xs md:text-sm font-bold flex items-center justify-center space-x-2 whitespace-nowrap transition-smooth ${
            activeTab === 'OTP_APPROVAL'
              ? 'bg-[#0061A4] text-white shadow-md shadow-[#0061A4]/20'
              : 'text-[#42474E] hover:bg-[#F6F9FD]'
          }`}
        >
          <KeyRound className="w-4 h-4" />
          <span>OTP Approval ({pendingSubmissions.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('BANNER_CUSTOMIZE')}
          className={`flex-1 py-3 px-4 rounded-xl text-xs md:text-sm font-bold flex items-center justify-center space-x-2 whitespace-nowrap transition-smooth ${
            activeTab === 'BANNER_CUSTOMIZE'
              ? 'bg-[#0061A4] text-white shadow-md shadow-[#0061A4]/20'
              : 'text-[#42474E] hover:bg-[#F6F9FD]'
          }`}
        >
          <Sliders className="w-4 h-4" />
          <span>Banner & Title</span>
        </button>

        <button
          onClick={() => setActiveTab('MEDIA_UPLOAD')}
          className={`flex-1 py-3 px-4 rounded-xl text-xs md:text-sm font-bold flex items-center justify-center space-x-2 whitespace-nowrap transition-smooth ${
            activeTab === 'MEDIA_UPLOAD'
              ? 'bg-[#0061A4] text-white shadow-md shadow-[#0061A4]/20'
              : 'text-[#42474E] hover:bg-[#F6F9FD]'
          }`}
        >
          <Image className="w-4 h-4" />
          <span>Media Upload ({mediaList.length})</span>
        </button>
      </div>

      {/* TAB 1: ALL SUBMISSIONS */}
      {activeTab === 'SUBMISSIONS' && (
        <div className="space-y-4">
          {/* Filter and Search Bar */}
          <div className="bg-white rounded-2xl p-4 border border-[#E0E3E8] shadow-sm flex flex-col md:flex-row gap-3 items-center justify-between">
            <div className="relative w-full md:w-80">
              <Search className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3 pointer-events-none" />
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search Player, EUID, OTP..."
                className="w-full pl-10 pr-4 py-2 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-xs font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
              />
            </div>

            <div className="flex items-center space-x-2 w-full md:w-auto overflow-x-auto">
              {(['ALL', 'PENDING', 'APPROVED', 'REJECTED'] as const).map((filter) => (
                <button
                  key={filter}
                  onClick={() => setStatusFilter(filter)}
                  className={`px-3 py-1.5 rounded-full text-xs font-bold transition-smooth ${
                    statusFilter === filter
                      ? 'bg-[#0061A4] text-white'
                      : 'bg-[#F6F9FD] text-[#72777F] border border-[#E0E3E8] hover:bg-[#D1E4FF]/40'
                  }`}
                >
                  {filter}
                </button>
              ))}
            </div>
          </div>

          {/* Submissions List */}
          {filteredSubmissions.length === 0 ? (
            <div className="bg-white rounded-3xl p-12 text-center border border-[#E0E3E8] space-y-3">
              <div className="w-12 h-12 rounded-full bg-[#F6F9FD] text-[#72777F] flex items-center justify-center mx-auto">
                <Search className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-base text-[#191C1E]">No submissions match your query</h3>
              <p className="text-xs text-[#72777F]">Try adjusting your search query or status filter.</p>
            </div>
          ) : (
            <div className="space-y-3">
              {filteredSubmissions.map((sub) => (
                <div
                  key={sub.id}
                  className="bg-white rounded-2xl p-5 border border-[#E0E3E8] shadow-sm space-y-4 hover:border-[#B0C9E8] transition-smooth"
                >
                  <div className="flex flex-wrap items-start justify-between gap-2">
                    <div className="space-y-1">
                      <div className="flex items-center space-x-2">
                        <h4 className="font-black text-base text-[#002E54]">
                          {sub.playerName}
                        </h4>
                        <span className="text-xs font-bold px-2 py-0.5 rounded-md bg-[#F6F9FD] border border-[#E0E3E8] text-[#72777F]">
                          Lvl {sub.level}
                        </span>
                        <span className="text-xs font-medium text-[#72777F]">
                          • {sub.serverRegion}
                        </span>
                      </div>

                      <div className="flex items-center space-x-3 text-xs text-[#72777F]">
                        <span>EUID: <strong className="font-mono text-[#191C1E]">{sub.euid}</strong></span>
                        <span>Phone: <strong className="text-[#191C1E]">{sub.phoneNumber}</strong></span>
                      </div>
                    </div>

                    <div className="flex items-center space-x-2">
                      <span
                        className={`px-3 py-1 rounded-full text-xs font-black uppercase tracking-wider ${
                          sub.status === 'APPROVED'
                            ? 'bg-emerald-100 text-emerald-800 border border-emerald-200'
                            : sub.status === 'REJECTED'
                            ? 'bg-red-100 text-red-800 border border-red-200'
                            : 'bg-amber-100 text-amber-800 border border-amber-200'
                        }`}
                      >
                        {sub.status}
                      </span>
                    </div>
                  </div>

                  {/* OTP & Action Row */}
                  <div className="flex flex-wrap items-center justify-between gap-3 pt-3 border-t border-[#E0E3E8]">
                    <div className="flex items-center space-x-2 bg-[#F6F9FD] px-3 py-1.5 rounded-xl border border-[#E0E3E8]">
                      <span className="text-[11px] font-bold text-[#72777F]">OTP TOKEN:</span>
                      <span className="font-mono font-black text-sm text-[#002E54] tracking-widest">
                        {sub.otpCode}
                      </span>
                      <button
                        onClick={() => handleCopyCode(sub.id, sub.otpCode)}
                        className="p-1 text-[#0061A4] hover:bg-white rounded transition-smooth"
                        title="Copy OTP"
                      >
                        {copiedId === sub.id ? (
                          <Check className="w-3.5 h-3.5 text-emerald-600" />
                        ) : (
                          <Copy className="w-3.5 h-3.5" />
                        )}
                      </button>
                    </div>

                    <div className="flex items-center space-x-2">
                      <button
                        onClick={() => onRegenerateOtp(sub.id)}
                        className="px-3 py-1.5 rounded-lg border border-[#E0E3E8] text-xs font-bold text-[#0061A4] hover:bg-[#D1E4FF]/40 flex items-center space-x-1 transition-smooth"
                        title="Generate New OTP"
                      >
                        <RefreshCw className="w-3.5 h-3.5" />
                        <span>Regen</span>
                      </button>

                      {sub.status !== 'APPROVED' && (
                        <button
                          onClick={() => onUpdateStatus(sub.id, 'APPROVED')}
                          className="px-3 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold flex items-center space-x-1 shadow-sm transition-smooth"
                        >
                          <Check className="w-3.5 h-3.5" />
                          <span>Approve</span>
                        </button>
                      )}

                      {sub.status !== 'REJECTED' && (
                        <button
                          onClick={() => onUpdateStatus(sub.id, 'REJECTED')}
                          className="px-3 py-1.5 rounded-lg bg-red-600 hover:bg-red-700 text-white text-xs font-bold flex items-center space-x-1 shadow-sm transition-smooth"
                        >
                          <X className="w-3.5 h-3.5" />
                          <span>Reject</span>
                        </button>
                      )}

                      <button
                        onClick={() => onDeleteSubmission(sub.id)}
                        className="p-1.5 rounded-lg text-[#72777F] hover:text-red-600 hover:bg-red-50 transition-smooth"
                        title="Delete record"
                      >
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* TAB 2: OTP APPROVAL QUEUE */}
      {activeTab === 'OTP_APPROVAL' && (
        <div className="space-y-4">
          <div className="bg-amber-50 rounded-2xl p-4 border border-amber-200 flex items-start space-x-3">
            <Clock className="w-5 h-5 text-amber-700 flex-shrink-0 mt-0.5" />
            <div>
              <h3 className="text-xs font-black uppercase tracking-wide text-amber-900">
                Pending Verification Codes Queue ({pendingSubmissions.length})
              </h3>
              <p className="text-xs text-amber-800 mt-0.5 leading-relaxed">
                Players with pending tokens are awaiting 20,000 Diamonds dispatch. Review EUID and mark as Approved to notify player and release redeem codes.
              </p>
            </div>
          </div>

          {pendingSubmissions.length === 0 ? (
            <div className="bg-white rounded-3xl p-12 text-center border border-[#E0E3E8] space-y-3">
              <div className="w-12 h-12 rounded-full bg-emerald-50 text-emerald-600 flex items-center justify-center mx-auto">
                <CheckCircle2 className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-base text-[#191C1E]">All tokens processed!</h3>
              <p className="text-xs text-[#72777F]">There are no pending submissions requiring OTP validation.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {pendingSubmissions.map((sub) => (
                <div
                  key={sub.id}
                  className="bg-white rounded-2xl p-5 border-2 border-amber-200 shadow-sm space-y-4"
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <h4 className="font-black text-base text-[#002E54]">{sub.playerName}</h4>
                      <p className="text-xs text-[#72777F]">
                        EUID: <strong className="font-mono text-[#191C1E]">{sub.euid}</strong> • Lvl {sub.level}
                      </p>
                    </div>
                    <span className="text-[10px] font-black uppercase px-2.5 py-1 rounded-full bg-amber-100 text-amber-900">
                      Pending
                    </span>
                  </div>

                  <div className="bg-[#D1E4FF]/60 rounded-xl p-3 text-center border border-[#B0C9E8]">
                    <span className="text-[10px] font-bold text-[#0061A4] uppercase tracking-wider block">
                      PLAYER VERIFICATION CODE
                    </span>
                    <span className="font-mono text-2xl font-black text-[#002E54] tracking-widest">
                      {sub.otpCode}
                    </span>
                  </div>

                  <div className="grid grid-cols-2 gap-2 pt-2">
                    <button
                      onClick={() => onUpdateStatus(sub.id, 'APPROVED')}
                      className="py-2.5 px-3 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold flex items-center justify-center space-x-1.5 shadow-sm transition-smooth"
                    >
                      <Check className="w-4 h-4" />
                      <span>Approve Code</span>
                    </button>

                    <button
                      onClick={() => onUpdateStatus(sub.id, 'REJECTED')}
                      className="py-2.5 px-3 rounded-xl bg-red-600 hover:bg-red-700 text-white text-xs font-bold flex items-center justify-center space-x-1.5 shadow-sm transition-smooth"
                    >
                      <X className="w-4 h-4" />
                      <span>Reject</span>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* TAB 3: BANNER & TITLE CUSTOMIZER */}
      {activeTab === 'BANNER_CUSTOMIZE' && (
        <div className="space-y-6">
          {/* Header Info */}
          <div className="bg-[#D1E4FF] rounded-2xl p-5 border border-[#B0C9E8] flex items-center space-x-4">
            <div className="w-12 h-12 rounded-xl bg-[#0061A4] text-white flex items-center justify-center shadow-md">
              <Sliders className="w-6 h-6" />
            </div>
            <div>
              <h3 className="font-black text-base text-[#002E54]">
                Banner & Title Customizer
              </h3>
              <p className="text-xs text-[#42474E] mt-0.5 font-medium">
                Update global app title, delivery guarantee message, promo banners, and accent colors live.
              </p>
            </div>
          </div>

          {/* Interactive Live Preview */}
          <div className="bg-white rounded-3xl p-6 border-2 border-[#B0C9E8] shadow-sm space-y-4">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2 text-xs font-black uppercase text-[#0061A4]">
                <Eye className="w-4 h-4" />
                <span>INTERACTIVE LIVE PREVIEW</span>
              </div>

              <span
                className={`text-[10px] font-black uppercase px-2.5 py-0.5 rounded-full ${
                  isBannerActive
                    ? 'bg-emerald-100 text-emerald-800'
                    : 'bg-gray-100 text-gray-600'
                }`}
              >
                {isBannerActive ? 'BANNER ACTIVE' : 'BANNER HIDDEN'}
              </span>
            </div>

            {/* Header Preview */}
            <div className="p-4 rounded-2xl bg-[#F6F9FD] border border-[#E0E3E8] text-center space-y-1">
              <h3
                className="text-lg font-black tracking-tight"
                style={{ color: bannerColorHex }}
              >
                {appTitle || "FREE WARRIOR GIVEAWAY"}
              </h3>
              <p className="text-xs font-bold text-[#72777F]">
                {appSubtitle || "20,000 DIAMONDS GIVEAWAY"}
              </p>
            </div>

            {/* Promotional Banner Preview */}
            {isBannerActive && (
              <div
                className="rounded-2xl p-4 border space-y-2"
                style={{
                  backgroundColor: `${bannerColorHex}12`,
                  borderColor: `${bannerColorHex}40`
                }}
              >
                <div className="flex items-center justify-between">
                  <span
                    className="px-2 py-0.5 rounded-full text-[10px] font-black text-white"
                    style={{ backgroundColor: bannerColorHex }}
                  >
                    {bannerBadge || "OFFICIAL BOUNTY"}
                  </span>
                  <span className="text-[11px] font-bold" style={{ color: bannerColorHex }}>
                    Within 1h - 1 Day
                  </span>
                </div>

                <h4 className="text-sm font-black text-[#002E54]">
                  {bannerTitle || "DIAMOND AIRDROP & REDEEM EVENT"}
                </h4>

                <p className="text-xs text-[#42474E] font-medium leading-relaxed">
                  {bannerSubtitle || "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."}
                </p>
              </div>
            )}
          </div>

          {/* Form Settings */}
          <form onSubmit={handleSaveBannerConfig} className="bg-white rounded-3xl p-6 md:p-8 border border-[#E0E3E8] shadow-sm space-y-5">
            <h3 className="font-black text-base text-[#002E54]">
              Configuration Parameters
            </h3>

            {/* App Title & Subtitle */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                  Global App Title
                </label>
                <input
                  type="text"
                  value={appTitle}
                  onChange={(e) => setAppTitle(e.target.value)}
                  placeholder="FREE WARRIOR GIVEAWAY"
                  className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-bold focus:bg-white focus:border-[#0061A4] outline-none"
                />
              </div>

              <div className="space-y-1.5">
                <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                  Event Subtitle
                </label>
                <input
                  type="text"
                  value={appSubtitle}
                  onChange={(e) => setAppSubtitle(e.target.value)}
                  placeholder="20,000 DIAMONDS GIVEAWAY"
                  className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
                />
              </div>
            </div>

            {/* Banner Toggle Switch */}
            <div className="flex items-center justify-between p-4 rounded-2xl bg-[#F6F9FD] border border-[#E0E3E8]">
              <div>
                <span className="text-sm font-black text-[#002E54] block">
                  Enable Dynamic Promotional Banner
                </span>
                <span className="text-xs text-[#72777F]">
                  Show official delivery guarantee & airdrop notices on the home screen.
                </span>
              </div>
              <input
                type="checkbox"
                checked={isBannerActive}
                onChange={(e) => setIsBannerActive(e.target.checked)}
                className="w-5 h-5 rounded text-[#0061A4] focus:ring-[#0061A4]"
              />
            </div>

            {/* Banner Headline */}
            <div className="space-y-1.5">
              <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                Banner Headline
              </label>
              <input
                type="text"
                value={bannerTitle}
                onChange={(e) => setBannerTitle(e.target.value)}
                placeholder="DIAMOND AIRDROP & REDEEM EVENT"
                className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
              />
            </div>

            {/* Banner Message / Delivery Guarantee */}
            <div className="space-y-1.5">
              <div className="flex justify-between items-center">
                <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                  Delivery Guarantee & Terms Message
                </label>
                <button
                  type="button"
                  onClick={() => setBannerSubtitle("Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.")}
                  className="text-xs font-bold text-[#0061A4] hover:underline"
                >
                  Set Official Guarantee Preset
                </button>
              </div>
              <textarea
                value={bannerSubtitle}
                onChange={(e) => setBannerSubtitle(e.target.value)}
                rows={2}
                placeholder="Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."
                className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
              />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                  Banner Badge Label
                </label>
                <input
                  type="text"
                  value={bannerBadge}
                  onChange={(e) => setBannerBadge(e.target.value)}
                  placeholder="OFFICIAL BOUNTY"
                  className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
                />
              </div>

              <div className="space-y-1.5">
                <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                  Banner Image URL (Optional)
                </label>
                <input
                  type="text"
                  value={bannerImageUrl}
                  onChange={(e) => setBannerImageUrl(e.target.value)}
                  placeholder="https://images.unsplash.com/..."
                  className="w-full px-4 py-2.5 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] outline-none"
                />
              </div>
            </div>

            {/* Accent Color Palette Selection */}
            <div className="space-y-2">
              <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                Banner Accent Color
              </label>
              <div className="flex items-center space-x-3">
                {[
                  { hex: '#0061A4', name: 'Deep Blue' },
                  { hex: '#FFB703', name: 'Amber Gold' },
                  { hex: '#198754', name: 'Emerald Green' },
                  { hex: '#DC3545', name: 'Crimson Red' },
                  { hex: '#00B4D8', name: 'Cyan' }
                ].map((c) => (
                  <button
                    key={c.hex}
                    type="button"
                    onClick={() => setBannerColorHex(c.hex)}
                    className={`w-9 h-9 rounded-full flex items-center justify-center shadow-sm transition-smooth ${
                      bannerColorHex === c.hex
                        ? 'ring-4 ring-[#0061A4]/30 scale-110'
                        : 'hover:scale-105'
                    }`}
                    style={{ backgroundColor: c.hex }}
                    title={c.name}
                  >
                    {bannerColorHex === c.hex && (
                      <Check className="w-4 h-4 text-white stroke-[3]" />
                    )}
                  </button>
                ))}
              </div>
            </div>

            {/* Submit & Reset Row */}
            <div className="flex items-center space-x-3 pt-4">
              <button
                type="button"
                onClick={() => {
                  setAppTitle("FREE WARRIOR GIVEAWAY");
                  setAppSubtitle("20,000 DIAMONDS GIVEAWAY");
                  setBannerTitle("DIAMOND AIRDROP & REDEEM EVENT");
                  setBannerSubtitle("Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.");
                  setBannerBadge("OFFICIAL BOUNTY");
                  setBannerImageUrl("");
                  setIsBannerActive(true);
                  setBannerColorHex("#0061A4");
                }}
                className="py-3 px-5 rounded-xl border border-[#E0E3E8] text-xs font-bold text-[#72777F] hover:bg-[#F6F9FD] flex items-center space-x-1.5 transition-smooth"
              >
                <RotateCcw className="w-4 h-4" />
                <span>Reset Defaults</span>
              </button>

              <button
                type="submit"
                className={`flex-1 py-3 px-6 rounded-xl font-black text-xs md:text-sm uppercase tracking-wider text-white shadow-md flex items-center justify-center space-x-2 transition-smooth ${
                  configSavedToast ? 'bg-[#198754]' : 'bg-[#0061A4] hover:bg-[#00487D]'
                }`}
              >
                {configSavedToast ? (
                  <>
                    <Check className="w-4 h-4 stroke-[3]" />
                    <span>SAVED LIVE & APPLIED!</span>
                  </>
                ) : (
                  <>
                    <Save className="w-4 h-4" />
                    <span>SAVE & APPLY CONFIG</span>
                  </>
                )}
              </button>
            </div>
          </form>
        </div>
      )}

      {/* TAB 4: MEDIA UPLOAD & PROOF SHOWCASE */}
      {activeTab === 'MEDIA_UPLOAD' && (
        <div className="space-y-6">
          <div className="flex flex-wrap items-center justify-between gap-3 bg-white rounded-2xl p-5 border border-[#E0E3E8] shadow-sm">
            <div>
              <h3 className="font-black text-base text-[#002E54]">
                Proof & Airdrop Media Gallery ({mediaList.length})
              </h3>
              <p className="text-xs text-[#72777F]">
                Upload official screenshot proofs, season banners, and redemption guides.
              </p>
            </div>

            <div className="flex items-center space-x-2">
              {mediaList.length > 0 && (
                <button
                  onClick={onClearAllMedia}
                  className="px-3.5 py-2 rounded-xl border border-red-200 text-red-600 hover:bg-red-50 text-xs font-bold transition-smooth"
                >
                  Clear All
                </button>
              )}

              <button
                onClick={() => setShowMediaModal(true)}
                className="px-4 py-2 rounded-xl bg-[#0061A4] hover:bg-[#00487D] text-white text-xs font-bold flex items-center space-x-1.5 shadow-sm transition-smooth"
              >
                <Plus className="w-4 h-4" />
                <span>Add Media</span>
              </button>
            </div>
          </div>

          {/* Media Grid */}
          {mediaList.length === 0 ? (
            <div className="bg-white rounded-3xl p-12 text-center border border-[#E0E3E8] space-y-3">
              <div className="w-12 h-12 rounded-full bg-[#F6F9FD] text-[#72777F] flex items-center justify-center mx-auto">
                <Image className="w-6 h-6" />
              </div>
              <h3 className="font-bold text-base text-[#191C1E]">No media uploaded yet</h3>
              <p className="text-xs text-[#72777F]">Click "Add Media" to showcase airdrop proofs on the Home screen.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {mediaList.map((media) => (
                <div
                  key={media.id}
                  className="bg-white rounded-2xl border border-[#E0E3E8] shadow-sm overflow-hidden flex flex-col justify-between"
                >
                  <div className="h-44 bg-slate-900 relative">
                    <img
                      src={media.url}
                      alt={media.title}
                      className="w-full h-full object-cover"
                      onError={(e) => {
                        (e.target as HTMLImageElement).src = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1200&q=80";
                      }}
                    />
                    <span className="absolute top-2 right-2 px-2.5 py-0.5 rounded-full bg-black/70 backdrop-blur-sm text-[10px] font-bold text-white uppercase">
                      {media.type}
                    </span>
                  </div>

                  <div className="p-4 space-y-2">
                    <h4 className="font-black text-sm text-[#002E54]">{media.title}</h4>
                    <p className="text-xs text-[#42474E] leading-relaxed">{media.description}</p>
                  </div>

                  <div className="p-4 pt-0 flex justify-end">
                    <button
                      onClick={() => onDeleteMedia(media.id)}
                      className="text-xs font-bold text-red-600 hover:text-red-800 flex items-center space-x-1"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                      <span>Delete</span>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}

          {/* Add Media Modal */}
          {showMediaModal && (
            <div className="fixed inset-0 z-50 bg-black/50 backdrop-blur-sm flex items-center justify-center p-4">
              <div className="bg-white rounded-3xl p-6 max-w-md w-full border border-[#E0E3E8] shadow-2xl space-y-4">
                <div className="flex justify-between items-center">
                  <h3 className="font-black text-lg text-[#002E54]">Add Proof Media</h3>
                  <button
                    onClick={() => setShowMediaModal(false)}
                    className="p-1 rounded-full text-[#72777F] hover:bg-gray-100"
                  >
                    <X className="w-5 h-5" />
                  </button>
                </div>

                <form onSubmit={handleAddMediaSubmit} className="space-y-3.5">
                  <div className="space-y-1">
                    <label className="block text-xs font-bold text-[#191C1E] uppercase">Title</label>
                    <input
                      type="text"
                      value={mediaTitle}
                      onChange={(e) => setMediaTitle(e.target.value)}
                      placeholder="e.g. Season 14 Mailbox Diamonds Proof"
                      required
                      className="w-full px-3.5 py-2 rounded-xl border border-[#E0E3E8] text-xs font-semibold focus:border-[#0061A4] outline-none"
                    />
                  </div>

                  <div className="space-y-1">
                    <label className="block text-xs font-bold text-[#191C1E] uppercase">Image URL</label>
                    <input
                      type="url"
                      value={mediaUrl}
                      onChange={(e) => setMediaUrl(e.target.value)}
                      placeholder="https://images.unsplash.com/..."
                      required
                      className="w-full px-3.5 py-2 rounded-xl border border-[#E0E3E8] text-xs font-semibold focus:border-[#0061A4] outline-none"
                    />
                  </div>

                  <div className="space-y-1">
                    <label className="block text-xs font-bold text-[#191C1E] uppercase">Description</label>
                    <textarea
                      value={mediaDesc}
                      onChange={(e) => setMediaDesc(e.target.value)}
                      placeholder="Proof of 20,000 Diamonds received in mailbox."
                      rows={2}
                      className="w-full px-3.5 py-2 rounded-xl border border-[#E0E3E8] text-xs font-semibold focus:border-[#0061A4] outline-none"
                    />
                  </div>

                  <div className="space-y-1">
                    <label className="block text-xs font-bold text-[#191C1E] uppercase">Media Type</label>
                    <select
                      value={mediaType}
                      onChange={(e) => setMediaType(e.target.value as 'IMAGE' | 'VIDEO')}
                      className="w-full px-3.5 py-2 rounded-xl border border-[#E0E3E8] text-xs font-semibold focus:border-[#0061A4] outline-none"
                    >
                      <option value="IMAGE">IMAGE</option>
                      <option value="VIDEO">VIDEO</option>
                    </select>
                  </div>

                  <div className="flex justify-end space-x-2 pt-2">
                    <button
                      type="button"
                      onClick={() => setShowMediaModal(false)}
                      className="px-4 py-2 rounded-xl border border-[#E0E3E8] text-xs font-bold text-[#72777F]"
                    >
                      Cancel
                    </button>
                    <button
                      type="submit"
                      className="px-5 py-2 rounded-xl bg-[#0061A4] hover:bg-[#00487D] text-white text-xs font-bold shadow-md"
                    >
                      Add to Showcase
                    </button>
                  </div>
                </form>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};
