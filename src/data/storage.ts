import { AppConfig, GiveawaySubmission, AdminMedia, ActiveClaim } from '../types';

export const DEFAULT_CONFIG: AppConfig = {
  appTitle: "FREE WARRIOR GIVEAWAY",
  appSubtitle: "20,000 DIAMONDS GIVEAWAY",
  bannerTitle: "DIAMOND AIRDROP & REDEEM EVENT",
  bannerSubtitle: "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
  bannerBadge: "OFFICIAL BOUNTY",
  bannerImageUrl: "",
  isBannerActive: true,
  bannerColorHex: "#0061A4"
};

export const INITIAL_SUBMISSIONS: GiveawaySubmission[] = [
  {
    id: "sub-1",
    playerName: "ShadowStriker",
    euid: "7829104",
    level: "75",
    phoneNumber: "+1 555-0192",
    serverRegion: "Global Server 1",
    timestamp: Date.now() - 3600000 * 3,
    status: "APPROVED",
    otpCode: "84920153",
    notes: "Verified player profile"
  },
  {
    id: "sub-2",
    playerName: "DragonLord99",
    euid: "9182374",
    level: "62",
    phoneNumber: "+1 555-0847",
    serverRegion: "Asia Server 2",
    timestamp: Date.now() - 3600000 * 8,
    status: "PENDING",
    otpCode: "39201847",
    notes: "Awaiting diamond packet dispatch"
  },
  {
    id: "sub-3",
    playerName: "ValkyrieQueen",
    euid: "5491820",
    level: "88",
    phoneNumber: "+1 555-0329",
    serverRegion: "Europe Server 1",
    timestamp: Date.now() - 3600000 * 24,
    status: "PENDING",
    otpCode: "71049283",
    notes: "Submitted via mobile client"
  }
];

export const INITIAL_MEDIA: AdminMedia[] = [
  {
    id: "med-1",
    title: "Official Season 14 Bounty Airdrop",
    description: "Proof of 20,000 Diamonds dispatch to player mailboxes",
    url: "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1200&q=80",
    type: "IMAGE",
    createdAt: Date.now() - 86400000 * 2
  },
  {
    id: "med-2",
    title: "Redeem Code Verification Notice",
    description: "Sample gift code redemption instructions",
    url: "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=1200&q=80",
    type: "IMAGE",
    createdAt: Date.now() - 86400000 * 5
  }
];

const STORAGE_KEYS = {
  CONFIG: 'warrior_app_config',
  SUBMISSIONS: 'warrior_submissions',
  MEDIA: 'warrior_media',
  ACTIVE_CLAIM: 'warrior_active_claim'
};

export const loadConfig = (): AppConfig => {
  try {
    const saved = localStorage.getItem(STORAGE_KEYS.CONFIG);
    return saved ? JSON.parse(saved) : DEFAULT_CONFIG;
  } catch (e) {
    return DEFAULT_CONFIG;
  }
};

export const saveConfig = (config: AppConfig) => {
  try {
    localStorage.setItem(STORAGE_KEYS.CONFIG, JSON.stringify(config));
  } catch (e) {
    console.error('Failed to save config', e);
  }
};

export const loadSubmissions = (): GiveawaySubmission[] => {
  try {
    const saved = localStorage.getItem(STORAGE_KEYS.SUBMISSIONS);
    return saved ? JSON.parse(saved) : INITIAL_SUBMISSIONS;
  } catch (e) {
    return INITIAL_SUBMISSIONS;
  }
};

export const saveSubmissions = (subs: GiveawaySubmission[]) => {
  try {
    localStorage.setItem(STORAGE_KEYS.SUBMISSIONS, JSON.stringify(subs));
  } catch (e) {
    console.error('Failed to save submissions', e);
  }
};

export const loadMedia = (): AdminMedia[] => {
  try {
    const saved = localStorage.getItem(STORAGE_KEYS.MEDIA);
    return saved ? JSON.parse(saved) : INITIAL_MEDIA;
  } catch (e) {
    return INITIAL_MEDIA;
  }
};

export const saveMedia = (media: AdminMedia[]) => {
  try {
    localStorage.setItem(STORAGE_KEYS.MEDIA, JSON.stringify(media));
  } catch (e) {
    console.error('Failed to save media', e);
  }
};

export const loadActiveClaim = (): ActiveClaim | null => {
  try {
    const saved = localStorage.getItem(STORAGE_KEYS.ACTIVE_CLAIM);
    return saved ? JSON.parse(saved) : null;
  } catch (e) {
    return null;
  }
};

export const saveActiveClaim = (claim: ActiveClaim | null) => {
  try {
    if (claim) {
      localStorage.setItem(STORAGE_KEYS.ACTIVE_CLAIM, JSON.stringify(claim));
    } else {
      localStorage.removeItem(STORAGE_KEYS.ACTIVE_CLAIM);
    }
  } catch (e) {
    console.error('Failed to save active claim', e);
  }
};
