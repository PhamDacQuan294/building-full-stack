export interface LoginPayload {
  email: string;
  password: string;
}

export interface JwtUser {
  sub: string;
  exp: number;
  iat: number;
}