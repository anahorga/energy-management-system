export type JwtClaims = { username?: string; role?: string; id?: number | string };

export function decodeJwt(token: string): JwtClaims | null {
    try {
        const payload = token.split(".")[1];
        const json = atob(payload.replace(/-/g, "+").replace(/_/g, "/"));
        return JSON.parse(decodeURIComponent(escape(json)));
    } catch {
        return null;
    }
}
