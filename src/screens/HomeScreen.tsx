import React from 'react';
import { AppConfig, AdminMedia } from '../types';
import { Gem, Clock, ShieldCheck, Sparkles, ArrowRight, UserCheck, KeyRound, Mailbox, Trophy, Lock } from 'lucide-react';

interface HomeScreenProps {
  appConfig: AppConfig;
  mediaList: AdminMedia[];
  onGetLink: () => void;
  onAdminClick: () => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({
  appConfig,
  mediaList,
  onGetLink,
  onAdminClick
}) => {
  const accentColor = appConfig.bannerColorHex || "#0061A4";

  return (
    <div className="space-y-6 pb-12 max-w-2xl mx-auto">
      {/* Dynamic Promotional Banner (if enabled by admin) */}
      {appConfig.isBannerActive && (
        <div
          className="rounded-2xl p-4 md:p-5 shadow-sm border transition-smooth relative overflow-hidden"
          style={{
            backgroundColor: `${accentColor}12`,
            borderColor: `${accentColor}40`
          }}
        >
          <div className="relative z-10 space-y-2.5">
            <div className="flex items-center justify-between">
              <span
                className="px-2.5 py-0.5 rounded-full text-[10px] font-black tracking-wider uppercase text-white shadow-sm"
                style={{ backgroundColor: accentColor }}
              >
                {appConfig.bannerBadge || "OFFICIAL BOUNTY"}
              </span>

              <div className="flex items-center space-x-1.5 text-xs font-bold" style={{ color: accentColor }}>
                <Clock className="w-3.5 h-3.5" />
                <span>Within 1h - 1 Day</span>
              </div>
            </div>

            <h2 className="text-base md:text-lg font-black tracking-tight text-[#002E54]">
              {appConfig.bannerTitle || "DIAMOND AIRDROP & REDEEM EVENT"}
            </h2>

            <p className="text-xs md:text-sm text-[#42474E] font-medium leading-relaxed">
              {appConfig.bannerSubtitle || "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."}
            </p>

            {appConfig.bannerImageUrl && (
              <div className="pt-2">
                <img
                  src={appConfig.bannerImageUrl}
                  alt="Promotional Banner"
                  className="w-full h-36 object-cover rounded-xl border border-white/60 shadow-sm"
                />
              </div>
            )}
          </div>
        </div>
      )}

      {/* Main 20,000 Diamonds Bounty Card */}
      <div className="relative rounded-3xl bg-gradient-to-b from-[#0061A4] via-[#00487D] to-[#002E54] text-white p-6 md:p-8 shadow-xl shadow-[#0061A4]/25 overflow-hidden">
        {/* Glow & Decorative elements */}
        <div className="absolute top-0 right-0 w-64 h-64 bg-[#FFB703]/15 rounded-full blur-3xl pointer-events-none" />
        <div className="absolute -bottom-10 -left-10 w-48 h-48 bg-[#00B4D8]/20 rounded-full blur-2xl pointer-events-none" />

        <div className="relative z-10 text-center space-y-4">
          <div className="inline-flex items-center space-x-2 px-3 py-1 rounded-full bg-white/10 backdrop-blur-md border border-white/20 text-xs font-bold tracking-wide">
            <Sparkles className="w-4 h-4 text-[#FFB703]" />
            <span>SEASON SPECIAL AIRDROP</span>
          </div>

          <div className="space-y-1">
            <div className="flex items-center justify-center space-x-2 text-[#FFB703]">
              <Gem className="w-8 h-8 md:w-10 md:h-10 fill-[#FFB703] drop-shadow-md animate-pulse" />
              <span className="text-3xl md:text-5xl font-black tracking-tight drop-shadow-sm font-mono">
                20,000
              </span>
            </div>
            <p className="text-lg md:text-xl font-extrabold tracking-wide text-white/90">
              FREE DIAMONDS BOUNTY
            </p>
          </div>

          {/* Prominent Delivery Guarantee Banner */}
          <div className="bg-white/10 backdrop-blur-md rounded-2xl p-3.5 border border-white/20 text-left space-y-1">
            <div className="flex items-center space-x-2 text-[#FFB703] text-xs font-black uppercase tracking-wider">
              <ShieldCheck className="w-4 h-4" />
              <span>Official Guarantee & Delivery</span>
            </div>
            <p className="text-xs md:text-sm text-white/95 font-medium leading-relaxed">
              Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.
            </p>
          </div>

          {/* Primary CTA Button */}
          <button
            onClick={onGetLink}
            className="w-full py-4 px-6 rounded-2xl bg-gradient-to-r from-[#FFB703] to-[#FB8500] hover:from-[#FFC024] hover:to-[#FF951A] text-[#002E54] font-black text-base md:text-lg tracking-wide uppercase shadow-lg shadow-[#FB8500]/35 flex items-center justify-center space-x-3 transform active:scale-[0.98] transition-smooth"
          >
            <span>GET LINK / CLAIM NOW</span>
            <ArrowRight className="w-5 h-5 stroke-[2.5]" />
          </button>
        </div>
      </div>

      {/* How to Claim Steps */}
      <div className="bg-white rounded-2xl p-5 md:p-6 border border-[#E0E3E8] shadow-sm space-y-4">
        <h3 className="text-sm font-bold uppercase tracking-wider text-[#0061A4] flex items-center gap-2">
          <Trophy className="w-4 h-4" />
          <span>How It Works (3 Easy Steps)</span>
        </h3>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-3.5">
          <div className="p-4 rounded-xl bg-[#F6F9FD] border border-[#E0E3E8] space-y-2">
            <div className="w-8 h-8 rounded-lg bg-[#D1E4FF] text-[#0061A4] flex items-center justify-center font-bold text-sm">
              1
            </div>
            <div className="font-bold text-sm text-[#191C1E]">Fill Game Details</div>
            <p className="text-xs text-[#42474E] leading-relaxed">
              Submit your EUID (In-Game ID), Player Name, and Account Level.
            </p>
          </div>

          <div className="p-4 rounded-xl bg-[#F6F9FD] border border-[#E0E3E8] space-y-2">
            <div className="w-8 h-8 rounded-lg bg-[#D1E4FF] text-[#0061A4] flex items-center justify-center font-bold text-sm">
              2
            </div>
            <div className="font-bold text-sm text-[#191C1E]">Get Verification Token</div>
            <p className="text-xs text-[#42474E] leading-relaxed">
              Receive your 8-digit unique admin verification token immediately.
            </p>
          </div>

          <div className="p-4 rounded-xl bg-[#F6F9FD] border border-[#E0E3E8] space-y-2">
            <div className="w-8 h-8 rounded-lg bg-[#D1E4FF] text-[#0061A4] flex items-center justify-center font-bold text-sm">
              3
            </div>
            <div className="font-bold text-sm text-[#191C1E]">Receive Diamonds</div>
            <p className="text-xs text-[#42474E] leading-relaxed">
              Either receive a redeem code or direct in-game diamonds in your mailbox.
            </p>
          </div>
        </div>
      </div>

      {/* Proofs & Official Media Showcase */}
      {mediaList.length > 0 && (
        <div className="bg-white rounded-2xl p-5 md:p-6 border border-[#E0E3E8] shadow-sm space-y-4">
          <div className="flex items-center justify-between">
            <h3 className="text-sm font-bold uppercase tracking-wider text-[#0061A4] flex items-center gap-2">
              <Mailbox className="w-4 h-4" />
              <span>Official Proofs & Airdrop Media</span>
            </h3>
            <span className="text-xs font-medium text-[#72777F]">
              {mediaList.length} updates
            </span>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {mediaList.map((media) => (
              <div
                key={media.id}
                className="group rounded-xl border border-[#E0E3E8] overflow-hidden bg-[#F6F9FD] hover:shadow-md transition-smooth"
              >
                <div className="h-40 overflow-hidden bg-slate-900 relative">
                  <img
                    src={media.url}
                    alt={media.title}
                    className="w-full h-full object-cover group-hover:scale-105 transition-smooth"
                    onError={(e) => {
                      (e.target as HTMLImageElement).src = "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1200&q=80";
                    }}
                  />
                  <span className="absolute top-2 right-2 px-2 py-0.5 rounded-full bg-black/60 backdrop-blur-sm text-[10px] font-bold text-white uppercase">
                    {media.type}
                  </span>
                </div>
                <div className="p-3.5 space-y-1">
                  <h4 className="font-bold text-sm text-[#002E54] line-clamp-1">
                    {media.title}
                  </h4>
                  <p className="text-xs text-[#42474E] line-clamp-2 leading-relaxed">
                    {media.description}
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Admin Login Quick Link at Bottom */}
      <div className="text-center pt-2">
        <button
          onClick={onAdminClick}
          className="inline-flex items-center space-x-2 text-xs font-bold text-[#72777F] hover:text-[#0061A4] px-4 py-2 rounded-full hover:bg-white transition-smooth"
        >
          <Lock className="w-3.5 h-3.5" />
          <span>Admin Portal Login (dkadmin)</span>
        </button>
      </div>
    </div>
  );
};
