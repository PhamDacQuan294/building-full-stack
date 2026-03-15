export type NewUserMailPayload = {
  actorId?: number | null;
  receiverId?: number | null;
  toEmail: string;
  fullName: string;
  email: string;
  password: string;
  roleName: string;
};

export type ResetPasswordMailPayload = {
  actorId?: number | null;
  receiverId?: number | null;
  toEmail: string;
  fullName: string;
  otp: string;
};

export type AssignmentMailPayload = {
  actorId?: number | null;
  receiverId?: number | null;
  toEmail: string;
  staffName: string;
  title: string;
  content: string;
  module: string;
  objectId?: number | null;
};

export type EmailLogItem = {
  id: number;
  actorId?: number;
  actorEmail?: string;
  actorName?: string;
  receiverId?: number;
  receiverEmail?: string;
  receiverName?: string;
  toEmail: string;
  subject: string;
  content: string;
  mailType: string;
  module: string;
  objectId?: number;
  sentSuccess: boolean;
  errorMessage?: string;
  createdDate: string;
};

export type EmailLogFilters = {
  toEmail: string;
  mailType: string;
  module: string;
  page: number;
  limit: number;
};