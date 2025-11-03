import { useAuth } from "../context/AuthContext";
export default function ClientDashboard(){ const { username, logout } = useAuth();
    return (<div style={{padding:24}}><h2>Client</h2><p>Welcome, {username}</p><button onClick={logout}>Logout</button></div>);
}
