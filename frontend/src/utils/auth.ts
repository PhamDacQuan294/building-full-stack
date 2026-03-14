export const TOKEN_KEY = "access_token";

export function saveToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function removeToken() {
  localStorage.removeItem(TOKEN_KEY);
}

export function parseJwt(token: string) {
  try {
    const base64 = token.split(".")[1];
    const json = atob(base64);
    return JSON.parse(json);
  } catch {
    return null;
  }
}

export function getRolesFromToken(): string[] {
  const token = getToken();
  if (!token) return [];

  const payload = parseJwt(token);
  if (!payload || !payload.roles) return [];

  return payload.roles;
}

export function getAuthoritiesFromToken(): string[] {
  const token = getToken();
  if (!token) return [];

  const payload = parseJwt(token);
  if (!payload || !payload.authorities) return [];

  return payload.authorities;
}