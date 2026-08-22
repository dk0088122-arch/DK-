export type AppScreen = 'HOME' | 'CLAIM_FORM' | 'VERIFICATION' | 'ADMIN_LOGIN' | 'ADMIN_DASHBOARD';

export type AdminTab = 'SUBMISSIONS' | 'OTP_APPROVAL' | 'BANNER_CUSTOMIZE' | 'MEDIA_UPLOAD';

export interface GiveawaySubmission {
  id: string;
  playerName: string;
  euid: string;
  level: string;
  phoneNumber: string;
  serverRegion: string;
  timestamp: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  otpCode: string;
  notes: string;
}

export interface AdminMedia {
  id: string;
  title: string;
  description: string;
  url: string;
  type: 'IMAGE' | 'VIDEO';
  createdAt: number;
}

export interface AppConfig {
  appTitle: string;
  appSubtitle: string;
  bannerTitle: string;
  bannerSubtitle: string;
  bannerBadge: string;
  bannerImageUrl: string;
  isBannerActive: boolean;
  bannerColorHex: string; // e.g. '#0061A4'
}

export interface ActiveClaim {
  id: string;
  playerName: string;
  euid: string;
  level: string;
  phoneNumber: string;
  serverRegion: string;
  otpCode: string;
  status: string;
  timestamp: number;
}
