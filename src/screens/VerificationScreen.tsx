import React, { useState } from 'react';
import { AppConfig, ActiveClaim } from '../types';
import { Shield, Lock, Copy, Check, Info, ArrowLeft, Clock, Gem, CheckCircle2 } from 'lucide-react';

interface VerificationScreenProps {
  activeClaim: ActiveClaim | null;
  appConfig: AppConfig;
  onBackToHome: () => void;
}

export const VerificationScreen: React.FC<VerificationScreenProps> = ({
  activeClaim,
  appConfig,
  onBackToHome
}) => {
  const [copied, setCopied] = useState(false);
  const code = activeClaim?.otpCode || "84920153";

  const handleCopy = () => {
    navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2500);
  };

  const formattedCode = code.length === 8 ? `${code.slice(0, 4)}   ${code.slice(4)}` : code;

  return (
    <div className="max-w-xl mx-auto space-y-6 pb-12">
      {/* Top Bar Navigation */}
      <div className="flex items-center justify-between">
        <button
          onClick={onBackToHome}
          className="inline-flex items-center space-x-1.5 text-xs font-bold text-[#0061A4] hover:underline"
        >
          <ArrowLeft className="w-4 h-4" />
          <span>Back to Home</span>
        </button>

        <span className="text-[11px] font-extrabold uppercase text-[#198754] bg-[#D1E7DD] px-3 py-1 rounded-full border border-[#BADBCC]">
          BOUNTY ISSUED
        </span>
      </div>

      {/* Success Badge */}
      <div className="text-center space-y-2">
        <div className="w-16 h-16 rounded-full bg-[#0061A4] text-white flex items-center justify-center mx-auto shadow-lg shadow-[#0061A4]/30">
          <Shield className="w-9 h-9 fill-white/20" />
        </div>
        <h2 className="text-2xl font-black text-[#002E54] tracking-tight">
          Claim Verified & Token Generated
        </h2>
        <p className="text-xs text-[#72777F] font-medium">
          Keep this 8-digit token safe for your diamond redemption confirmation.
        </p>
      </div>

      {/* Prominent Delivery Guarantee Banner */}
      <div className="bg-white rounded-2xl p-4 md:p-5 border-2 border-[#B0C9E8] shadow-sm space-y-2">
        <div className="flex items-center justify-between">
          <span className="text-[10px] font-black uppercase tracking-wider text-[#002E54] bg-[#D1E4FF] px-2.5 py-1 rounded-full">
            BOUNTY STATUS: ISSUED
          </span>

          <div className="flex items-center space-x-1 text-xs font-bold text-[#0061A4]">
            <Clock className="w-3.5 h-3.5" />
            <span>Within 1h - 1 Day</span>
          </div>
        </div>

        <p className="text-sm md:text-base font-black text-[#002E54] leading-snug">
          Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.
        </p>
      </div>

      {/* Main Verification Code Card */}
      <div className="bg-[#D1E4FF] rounded-3xl p-6 border-2 border-[#B0C9E8] shadow-md space-y-5 text-center">
        <div className="inline-flex items-center space-x-1.5 px-3 py-1 rounded-full bg-[#0061A4]/15 text-[#0061A4] text-[11px] font-extrabold uppercase tracking-wider">
          <Lock className="w-3.5 h-3.5" />
          <span>READ-ONLY ADMIN TOKEN</span>
        </div>

        {/* 8-Digit Display Box */}
        <div className="bg-white rounded-2xl py-5 px-4 border-2 border-[#B0C9E8] shadow-inner">
          <span className="font-mono text-3xl md:text-4xl font-black text-[#002E54] tracking-[0.25em]">
            {formattedCode}
          </span>
        </div>

        {/* Copy Button */}
        <button
          onClick={handleCopy}
          className={`w-full py-3.5 px-6 rounded-full font-black text-sm uppercase tracking-wider shadow-md flex items-center justify-center space-x-2 transition-smooth ${
            copied
              ? 'bg-[#198754] text-white shadow-[#198754]/30'
              : 'bg-[#0061A4] hover:bg-[#00487D] text-white shadow-[#0061A4]/30'
          }`}
        >
          {copied ? (
            <>
              <Check className="w-4 h-4 stroke-[3]" />
              <span>CODE COPIED TO CLIPBOARD!</span>
            </>
          ) : (
            <>
              <Copy className="w-4 h-4" />
              <span>COPY VERIFICATION CODE</span>
            </>
          )}
        </button>
      </div>

      {/* Claim Summary Card */}
      {activeClaim && (
        <div className="bg-white rounded-2xl p-5 border border-[#E0E3E8] shadow-sm space-y-3">
          <h3 className="text-xs font-black uppercase tracking-wider text-[#0061A4]">
            CLAIM DETAILS SUMMARY
          </h3>

          <div className="divide-y divide-[#E0E3E8] text-xs">
            <div className="py-2 flex justify-between items-center">
              <span className="text-[#72777F]">Player Name</span>
              <span className="font-bold text-[#191C1E]">{activeClaim.playerName}</span>
            </div>
            <div className="py-2 flex justify-between items-center">
              <span className="text-[#72777F]">EUID (Account ID)</span>
              <span className="font-bold font-mono text-[#191C1E]">{activeClaim.euid}</span>
            </div>
            <div className="py-2 flex justify-between items-center">
              <span className="text-[#72777F]">Account Level</span>
              <span className="font-bold text-[#191C1E]">Level {activeClaim.level}</span>
            </div>
            <div className="py-2 flex justify-between items-center">
              <span className="text-[#72777F]">Server Region</span>
              <span className="font-bold text-[#191C1E]">{activeClaim.serverRegion}</span>
            </div>
            <div className="py-2 flex justify-between items-center">
              <span className="text-[#72777F]">Bounty Reward</span>
              <span className="font-black text-[#FB8500] flex items-center gap-1">
                <Gem className="w-3.5 h-3.5 fill-[#FFB703]" />
                20,000 Diamonds
              </span>
            </div>
          </div>
        </div>
      )}

      {/* Disclaimer Note */}
      <div className="bg-white rounded-2xl p-5 border border-[#E0E3E8] shadow-sm space-y-2">
        <div className="flex items-center space-x-2 text-[#0061A4]">
          <Info className="w-4 h-4" />
          <h4 className="text-xs font-black uppercase tracking-wide">Important Disclaimer</h4>
        </div>
        <p className="text-xs text-[#72777F] leading-relaxed">
          Disclaimer: This unique 8-digit verification code is generated directly by the admin server for your 20,000 Diamonds redemption. Please present this code or keep it safe. Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.
        </p>
      </div>

      {/* Back to Home Button */}
      <button
        onClick={onBackToHome}
        className="w-full py-3.5 px-6 rounded-full border-2 border-[#0061A4] text-[#0061A4] hover:bg-[#D1E4FF]/40 font-black text-sm uppercase tracking-wider transition-smooth"
      >
        BACK TO HOME
      </button>
    </div>
  );
};
