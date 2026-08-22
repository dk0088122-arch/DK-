import React, { useState } from 'react';
import { AppConfig, GiveawaySubmission, ActiveClaim } from '../types';
import { Shield, Gem, User, Hash, Trophy, Phone, Globe, AlertCircle, ArrowLeft, CheckCircle2, Clock } from 'lucide-react';

interface ClaimFormScreenProps {
  appConfig: AppConfig;
  onSubmit: (submission: Omit<GiveawaySubmission, 'id' | 'timestamp' | 'status' | 'otpCode' | 'notes'>) => void;
  onBack: () => void;
  onAdminClick: () => void;
}

export const ClaimFormScreen: React.FC<ClaimFormScreenProps> = ({
  appConfig,
  onSubmit,
  onBack,
  onAdminClick
}) => {
  const [playerName, setPlayerName] = useState('');
  const [euid, setEuid] = useState('');
  const [level, setLevel] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [serverRegion, setServerRegion] = useState('Global Server 1');
  const [agreed, setAgreed] = useState(true);
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (!playerName.trim()) {
      setError('Please enter your in-game Player Name.');
      return;
    }
    if (!euid.trim() || euid.trim().length < 5) {
      setError('Please enter a valid EUID (minimum 5 digits).');
      return;
    }
    if (!level.trim() || isNaN(Number(level)) || Number(level) < 1) {
      setError('Please enter a valid account level (1 - 200).');
      return;
    }
    if (!phoneNumber.trim()) {
      setError('Please enter a contact phone number for SMS/WhatsApp alert.');
      return;
    }
    if (!agreed) {
      setError('Please acknowledge the delivery terms to proceed.');
      return;
    }

    setIsSubmitting(true);
    setTimeout(() => {
      onSubmit({
        playerName: playerName.trim(),
        euid: euid.trim(),
        level: level.trim(),
        phoneNumber: phoneNumber.trim(),
        serverRegion
      });
      setIsSubmitting(false);
    }, 600);
  };

  return (
    <div className="max-w-xl mx-auto space-y-6 pb-12">
      {/* Top Breadcrumb / Return */}
      <div className="flex items-center justify-between">
        <button
          onClick={onBack}
          className="inline-flex items-center space-x-1.5 text-xs font-bold text-[#0061A4] hover:underline"
        >
          <ArrowLeft className="w-4 h-4" />
          <span>Back to Event Details</span>
        </button>

        <div className="flex items-center space-x-1 text-xs font-black text-[#FFB703] bg-[#002E54] px-3 py-1 rounded-full">
          <Gem className="w-3.5 h-3.5 fill-[#FFB703]" />
          <span>20,000 Diamonds</span>
        </div>
      </div>

      {/* Main Form Container */}
      <div className="bg-white rounded-3xl p-6 md:p-8 border border-[#E0E3E8] shadow-md space-y-6">
        <div className="text-center space-y-2">
          <div className="inline-flex p-3 rounded-2xl bg-[#D1E4FF] text-[#0061A4] mb-1">
            <Shield className="w-7 h-7" />
          </div>
          <h2 className="text-xl md:text-2xl font-black text-[#002E54] tracking-tight">
            Claim Player Portal
          </h2>
          <p className="text-xs md:text-sm text-[#72777F] font-medium">
            Enter your game credentials to generate your admin verification token.
          </p>
        </div>

        {/* Delivery Guarantee Notice Banner */}
        <div className="rounded-2xl p-4 bg-[#F6F9FD] border border-[#B0C9E8] space-y-2">
          <div className="flex items-center justify-between">
            <span className="text-[11px] font-black uppercase text-[#0061A4] tracking-wide flex items-center gap-1.5">
              <CheckCircle2 className="w-4 h-4 text-[#198754]" />
              Official Delivery Policy
            </span>
            <span className="text-[11px] font-bold text-[#0061A4] flex items-center gap-1">
              <Clock className="w-3 h-3" /> 1 Hour - 1 Day
            </span>
          </div>
          <p className="text-xs text-[#191C1E] font-semibold leading-relaxed">
            "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."
          </p>
        </div>

        {error && (
          <div className="p-3.5 rounded-xl bg-red-50 border border-red-200 text-red-700 text-xs font-semibold flex items-center space-x-2">
            <AlertCircle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Player In-Game Name */}
          <div className="space-y-1.5">
            <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
              In-Game Player Name <span className="text-red-500">*</span>
            </label>
            <div className="relative">
              <User className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
              <input
                type="text"
                value={playerName}
                onChange={(e) => setPlayerName(e.target.value)}
                placeholder="e.g. ShadowWarrior_99"
                className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
              />
            </div>
          </div>

          {/* EUID (Player ID) */}
          <div className="space-y-1.5">
            <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
              EUID (In-Game Account ID) <span className="text-red-500">*</span>
            </label>
            <div className="relative">
              <Hash className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
              <input
                type="text"
                value={euid}
                onChange={(e) => setEuid(e.target.value.replace(/\D/g, ''))}
                placeholder="e.g. 7849201"
                className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-mono font-bold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
              />
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {/* Account Level */}
            <div className="space-y-1.5">
              <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                Account Level <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <Trophy className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
                <input
                  type="number"
                  value={level}
                  onChange={(e) => setLevel(e.target.value)}
                  placeholder="e.g. 65"
                  min="1"
                  max="300"
                  className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
                />
              </div>
            </div>

            {/* Server / Region */}
            <div className="space-y-1.5">
              <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
                Server Region
              </label>
              <div className="relative">
                <Globe className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
                <select
                  value={serverRegion}
                  onChange={(e) => setServerRegion(e.target.value)}
                  className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth cursor-pointer"
                >
                  <option value="Global Server 1">Global Server 1</option>
                  <option value="Global Server 2">Global Server 2</option>
                  <option value="Asia Server 1">Asia Server 1</option>
                  <option value="Asia Server 2">Asia Server 2</option>
                  <option value="Europe Server 1">Europe Server 1</option>
                  <option value="North America 1">North America 1</option>
                </select>
              </div>
            </div>
          </div>

          {/* Contact Phone / WhatsApp */}
          <div className="space-y-1.5">
            <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
              Phone / WhatsApp (For OTP Dispatch) <span className="text-red-500">*</span>
            </label>
            <div className="relative">
              <Phone className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
              <input
                type="text"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                placeholder="+1 555 0192"
                className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
              />
            </div>
          </div>

          {/* Terms Checkbox */}
          <div className="pt-2">
            <label className="flex items-start space-x-3 cursor-pointer select-none">
              <input
                type="checkbox"
                checked={agreed}
                onChange={(e) => setAgreed(e.target.checked)}
                className="mt-0.5 w-4 h-4 rounded text-[#0061A4] focus:ring-[#0061A4] border-gray-300"
              />
              <span className="text-xs text-[#42474E] font-medium leading-normal">
                I understand that my unique 8-digit verification code will be generated immediately and diamonds will be dispatched within 1 hour to 1 day.
              </span>
            </label>
          </div>

          {/* Submit Button */}
          <div className="pt-3">
            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full py-4 px-6 rounded-2xl bg-gradient-to-r from-[#0061A4] to-[#00487D] hover:from-[#0072C2] hover:to-[#005594] text-white font-black text-sm md:text-base tracking-wider uppercase shadow-lg shadow-[#0061A4]/30 flex items-center justify-center space-x-2 transform active:scale-[0.98] transition-smooth disabled:opacity-60"
            >
              {isSubmitting ? (
                <span>GENERATING TOKEN...</span>
              ) : (
                <>
                  <Shield className="w-5 h-5" />
                  <span>SUBMIT CLAIM & GET TOKEN</span>
                </>
              )}
            </button>
          </div>
        </form>
      </div>

      {/* Admin shortcut */}
      <div className="text-center">
        <button
          onClick={onAdminClick}
          className="text-xs font-semibold text-[#72777F] hover:text-[#0061A4] underline"
        >
          Are you an administrator? Log in here
        </button>
      </div>
    </div>
  );
};
