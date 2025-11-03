import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { createUserAsAdmin } from "../lib/adminProvision";
import type { UserRole } from "../types/auth";

const roles: UserRole[] = ["USER", "ADMIN"];

export default function AdminDashboard() {
    const { username, role, logout } = useAuth();
    const [showForm, setShowForm] = useState(false);

    const [f, setF] = useState({
        username: "",
        password: "",
        userRole: "USER" as UserRole,
        firstName: "",
        lastName: "",
        address: "",
        email: "",
    });

    const [loading, setLoading] = useState(false);
    const [err, setErr] = useState<string | null>(null);
    const [ok, setOk] = useState<string | null>(null);

    const onCreate = async (e: React.FormEvent) => {
        e.preventDefault();
        setErr(null); setOk(null); setLoading(true);
        try {
            const { role: createdRole, id } = await createUserAsAdmin(
                { username: f.username, password: f.password, userRole: f.userRole },
                { firstName: f.firstName, lastName: f.lastName, address: f.address, email: f.email }
            );
            setOk(`User creat: ID=${id}, rol=${createdRole}.`);
            setF({ username: "", password: "", userRole: "USER", firstName: "", lastName: "", address: "", email: "" });
        } catch (e: any) {
            setErr(e?.response?.data?.error || e?.message || "Create user failed");
        } finally { setLoading(false); }
    };

    return (
        <div style={{ padding: 24, fontFamily: "sans-serif" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <h2>Admin dashboard</h2>
                <div>
                    <span style={{ marginRight: 16 }}>Logged in: {username}</span>
                    <button onClick={logout}>Logout</button>
                </div>
            </div>

            <hr style={{ margin: "16px 0" }} />

            {role !== "ADMIN" ? (
                <p style={{ color: "crimson" }}>Nu ai rol de ADMIN.</p>
            ) : (
                <>
                    <button onClick={() => setShowForm(s => !s)}>{showForm ? "Close" : "Add user"}</button>

                    {showForm && (
                        <form onSubmit={onCreate} style={{ marginTop: 16, maxWidth: 560, display: "grid", gap: 8 }}>
                            <fieldset style={{ border: "1px solid #ccc", padding: 12 }}>
                                <legend>Auth</legend>
                                <label>Username
                                    <input value={f.username} onChange={e=>setF(s=>({ ...s, username: e.target.value }))} required />
                                </label>
                                <label>Password
                                    <input type="password" value={f.password} onChange={e=>setF(s=>({ ...s, password: e.target.value }))} required />
                                </label>
                                <label>Role
                                    <select value={f.userRole} onChange={e=>setF(s=>({ ...s, userRole: e.target.value as UserRole }))}>
                                        {roles.map(r => <option key={r} value={r}>{r}</option>)}
                                    </select>
                                </label>
                            </fieldset>

                            <fieldset style={{ border: "1px solid #ccc", padding: 12 }}>
                                <legend>Profile</legend>
                                <label>First name
                                    <input value={f.firstName} onChange={e=>setF(s=>({ ...s, firstName: e.target.value }))} required />
                                </label>
                                <label>Last name
                                    <input value={f.lastName} onChange={e=>setF(s=>({ ...s, lastName: e.target.value }))} required />
                                </label>
                                <label>Address
                                    <input value={f.address} onChange={e=>setF(s=>({ ...s, address: e.target.value }))} required />
                                </label>
                                <label>Email
                                    <input type="email" value={f.email} onChange={e=>setF(s=>({ ...s, email: e.target.value }))} required />
                                </label>
                            </fieldset>

                            <div style={{ display: "flex", gap: 8 }}>
                                <button type="submit" disabled={loading}>{loading ? "Creating..." : "Create user"}</button>
                                <button type="button" onClick={()=>setShowForm(false)}>Cancel</button>
                            </div>

                            {err && <p style={{ color: "crimson" }}>{err}</p>}
                            {ok && <p style={{ color: "green" }}>{ok}</p>}
                        </form>
                    )}
                </>
            )}
        </div>
    );
}
