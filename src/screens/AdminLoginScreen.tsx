import React, { useState } from 'react';
import { Lock, Shield, User, KeyRound, AlertCircle, ArrowLeft, Sparkles } from 'lucide-react';

interface AdminLoginScreenProps {
  onSuccess: () => void;
  onBack: () => void;
}

export const AdminLoginScreen: React.FC<AdminLoginScreenProps> = ({
  onSuccess,
  onBack
}) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (!username.trim() || !password.trim()) {
      setError('Please provide both username and password.');
      return;
    }

    setLoading(true);
    setTimeout(() => {
      if (username.trim() === 'dkadmin' && password === 'Admin@123') {
        onSuccess();
      } else {
        setError('Invalid admin credentials. (Default: dkadmin / Admin@123)');
      }
      setLoading(false);
    }, 400);
  };

  const handleQuickFill = () => {
    setUsername('dkadmin');
    setPassword('Admin@123');
    setError('');
  };

  return (
    <div className="max-w-md mx-auto space-y-6 pb-12 pt-4">
      <button
        onClick={onBack}
        className="inline-flex items-center space-x-1.5 text-xs font-bold text-[#0061A4] hover:underline"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Return to Giveaway Home</span>
      </button>

      <div className="bg-white rounded-3xl p-6 md:p-8 border border-[#E0E3E8] shadow-md space-y-6">
        <div className="text-center space-y-2">
          <div className="w-14 h-14 rounded-2xl bg-[#0061A4] text-white flex items-center justify-center mx-auto shadow-md shadow-[#0061A4]/30">
            <Lock className="w-7 h-7" />
          </div>
          <h2 className="text-2xl font-black text-[#002E54] tracking-tight">
            Administrator Portal
          </h2>
          <p className="text-xs text-[#72777F] font-medium">
            Manage OTP tokens, player submissions, banners, and media.
          </p>
        </div>

        {error && (
          <div className="p-3.5 rounded-xl bg-red-50 border border-red-200 text-red-700 text-xs font-semibold flex items-center space-x-2">
            <AlertCircle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleLogin} className="space-y-4">
          <div className="space-y-1.5">
            <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
              Username
            </label>
            <div className="relative">
              <User className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="dkadmin"
                className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
              />
            </div>
          </div>

          <div className="space-y-1.5">
            <label className="block text-xs font-bold text-[#191C1E] uppercase tracking-wide">
              Password
            </label>
            <div className="relative">
              <KeyRound className="w-4 h-4 text-[#72777F] absolute left-3.5 top-3.5 pointer-events-none" />
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                className="w-full pl-10 pr-4 py-3 rounded-xl border border-[#E0E3E8] bg-[#F6F9FD] text-sm font-semibold focus:bg-white focus:border-[#0061A4] focus:ring-2 focus:ring-[#D1E4FF] outline-none transition-smooth"
              />
            </div>
          </div>

          {/* Quick Demo Fill Helper */}
          <div className="pt-1 flex justify-end">
            <button
              type="button"
              onClick={handleQuickFill}
              className="text-xs font-bold text-[#0061A4] hover:text-[#00487D] flex items-center space-x-1"
            >
              <Sparkles className="w-3.5 h-3.5 text-[#FFB703]" />
              <span>Fill Demo Credentials (dkadmin)</span>
            </button>
          </div>

          <div className="pt-2">
            <button
              type="submit"
              disabled={loading}
              className="w-full py-3.5 px-6 rounded-xl bg-[#0061A4] hover:bg-[#00487D] text-white font-bold text-sm uppercase tracking-wider shadow-lg shadow-[#0061A4]/30 flex items-center justify-center space-x-2 transition-smooth disabled:opacity-60"
            >
              {loading ? 'Authenticating...' : 'Sign In as Admin'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
