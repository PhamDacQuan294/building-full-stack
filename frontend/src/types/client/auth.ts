export type ClientProfile = {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  role: string;
};

export type ClientLoginResponse = {
  token: string;
  user: ClientProfile;
};

export type ClientRegisterPayload = {
  fullName: string;
  email: string;
  phone: string;
  password: string;
};

export type ClientLoginPayload = {
  email: string;
  password: string;
};

export type ClientForgotPasswordPayload = {
  email: string;
};

export type ClientResetPasswordPayload = {
  email: string;
  otp: string;
  newPassword: string;
};

export type ClientChangePasswordPayload = {
  oldPassword: string;
  newPassword: string;
};

export type ClientUpdateProfilePayload = {
  fullName: string;
  email: string;
  phone: string;
};