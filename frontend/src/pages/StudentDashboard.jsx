import React, { useState, useEffect } from 'react';
import api from '../services/api';

const StudentDashboard = () => {
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchResults();
    }, []);

    useEffect(() => {
        if (error) {
            const t = setTimeout(() => setError(null), 4000);
            return () => clearTimeout(t);
        }
    }, [error]);

    const fetchResults = async () => {
        setLoading(true);
        try {
            // In a real app, we'd get the student ID from the auth token or a /me endpoint
            // For now, we'll assume the backend handles returning only the logged-in student's results
            // or we'd fetch based on a stored student ID.
            const res = await api.get('/results/me'); 
            setResults(res.data);
        } catch (err) {
            setError("Failed to fetch results. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="dashboard-layout">
            <div className="sidebar">
                <h2 style={{ color: 'var(--primary)', marginBottom: '2rem' }}>EduResult</h2>
                <nav>
                    <div style={{ color: 'var(--primary)', fontWeight: 'bold', marginBottom: '1rem' }}>Student Portal</div>
                    <div style={{ color: 'var(--text-muted)', marginBottom: '1rem' }}>My Results</div>
                </nav>
                <div style={{ marginTop: 'auto', paddingTop: '2rem' }}>
                    <button 
                        onClick={() => { localStorage.clear(); window.location.href = '/login'; }}
                        style={{ width: '100%', padding: '0.75rem', background: 'var(--glass)', color: 'var(--error)', borderRadius: '0.5rem', border: '1px solid var(--error)' }}
                    >
                        Logout
                    </button>
                </div>
            </div>
            <div className="main-content">
                {error && (
                    <div style={{ background: '#fee2e2', color: '#991b1b', padding: '1rem', borderRadius: '0.5rem', marginBottom: '1rem', border: '1px solid #fecaca' }}>
                        {error}
                    </div>
                )}

                <h1>My Academic Record</h1>
                <p style={{ color: 'var(--text-muted)', marginBottom: '2rem' }}>View your semester-wise performance and CGPA.</p>

                <div className="card">
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)' }}>
                                <th style={{ padding: '1rem' }}>Semester</th>
                                <th style={{ padding: '1rem' }}>SGPA</th>
                                <th style={{ padding: '1rem' }}>CGPA</th>
                                <th style={{ padding: '1rem' }}>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {loading ? (
                                <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>Loading your results...</td></tr>
                            ) : results.length === 0 ? (
                                <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>No results published yet</td></tr>
                            ) : (
                                results.map(r => (
                                    <tr key={r.id} style={{ borderBottom: '1px solid var(--border)' }}>
                                        <td style={{ padding: '1rem' }}>Semester {r.semester}</td>
                                        <td style={{ padding: '1rem', fontWeight: 'bold' }}>{r.sgpa.toFixed(2)}</td>
                                        <td style={{ padding: '1rem' }}>{r.cgpa.toFixed(2)}</td>
                                        <td style={{ padding: '1rem' }}>
                                            <span style={{ 
                                                padding: '0.25rem 0.75rem', 
                                                borderRadius: '1rem', 
                                                fontSize: '0.875rem',
                                                background: r.sgpa >= 4.0 ? '#d1fae5' : '#fee2e2',
                                                color: r.sgpa >= 4.0 ? '#065f46' : '#991b1b'
                                            }}>
                                                {r.sgpa >= 4.0 ? 'PASS' : 'FAIL'}
                                            </span>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default StudentDashboard;
