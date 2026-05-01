import React, { useState, useEffect } from 'react';
import api from '../services/api';

const AdminDashboard = () => {
    const [students, setStudents] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newStudent, setNewStudent] = useState({ name: '', email: '', branch: '', currentSemester: 1, username: '', password: '' });
    const [newSubject, setNewSubject] = useState({ name: '', credits: 3, semester: 1 });
    const [activeTab, setActiveTab] = useState('students');

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        if (error) {
            const t = setTimeout(() => setError(null), 4000);
            return () => clearTimeout(t);
        }
    }, [error]);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [sRes, subRes] = await Promise.all([
                api.get('/students'),
                api.get('/subjects')
            ]);
            setStudents(sRes.data);
            setSubjects(subRes.data);
        } catch (err) {
            setError("Failed to load data. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleAddStudent = async (e) => {
        e.preventDefault();
        try {
            await api.post('/students', newStudent);
            setShowForm(false);
            fetchData();
            setNewStudent({ name: '', email: '', branch: '', currentSemester: 1, username: '', password: '' });
        } catch (err) {
            setError("Error adding student. Please check the details.");
        }
    };

    const handleAddSubject = async (e) => {
        e.preventDefault();
        try {
            await api.post('/subjects', newSubject);
            setShowForm(false);
            fetchData();
            setNewSubject({ name: '', credits: 3, semester: 1 });
        } catch (err) {
            setError("Error adding subject.");
        }
    };

    const handleDeleteStudent = async (id) => {
        if (!window.confirm('Delete this student?')) return;
        try {
            await api.delete(`/students/${id}`);
            fetchData();
        } catch (err) {
            setError("Error deleting student.");
        }
    };

    const handleDeleteSubject = async (id) => {
        if (!window.confirm('Delete this subject?')) return;
        try {
            await api.delete(`/subjects/${id}`);
            fetchData();
        } catch (err) {
            setError("Error deleting subject.");
        }
    };

    return (
        <div className="dashboard-layout">
            <div className="sidebar">
                <h2 style={{ color: 'var(--primary)', marginBottom: '2rem' }}>EduResult</h2>
                <nav>
                    <div 
                        className={`nav-item ${activeTab === 'students' ? 'active' : ''}`}
                        onClick={() => setActiveTab('students')}
                        style={{ cursor: 'pointer', padding: '0.75rem', borderRadius: '0.5rem', marginBottom: '0.5rem', color: activeTab === 'students' ? 'var(--primary)' : 'var(--text-muted)' }}
                    >
                        Manage Students
                    </div>
                    <div 
                        className={`nav-item ${activeTab === 'subjects' ? 'active' : ''}`}
                        onClick={() => setActiveTab('subjects')}
                        style={{ cursor: 'pointer', padding: '0.75rem', borderRadius: '0.5rem', color: activeTab === 'subjects' ? 'var(--primary)' : 'var(--text-muted)' }}
                    >
                        Manage Subjects
                    </div>
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

                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                    <h1>{activeTab === 'students' ? 'Student Records' : 'Subject Catalog'}</h1>
                    <button className="btn-primary" onClick={() => setShowForm(!showForm)}>
                        {showForm ? 'Close Form' : `Add New ${activeTab === 'students' ? 'Student' : 'Subject'}`}
                    </button>
                </div>

                {showForm && activeTab === 'students' && (
                    <div className="card" style={{ marginBottom: '2rem' }}>
                        <form onSubmit={handleAddStudent} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                            <input placeholder="Full Name" value={newStudent.name} onChange={e => setNewStudent({...newStudent, name: e.target.value})} required />
                            <input placeholder="Email" type="email" value={newStudent.email} onChange={e => setNewStudent({...newStudent, email: e.target.value})} required />
                            <input placeholder="Branch" value={newStudent.branch} onChange={e => setNewStudent({...newStudent, branch: e.target.value})} required />
                            <input placeholder="Semester" type="number" value={newStudent.currentSemester} onChange={e => setNewStudent({...newStudent, currentSemester: e.target.value})} required />
                            <input placeholder="Username" value={newStudent.username} onChange={e => setNewStudent({...newStudent, username: e.target.value})} required />
                            <input placeholder="Password" type="password" value={newStudent.password} onChange={e => setNewStudent({...newStudent, password: e.target.value})} required />
                            <button type="submit" className="btn-primary" style={{ gridColumn: 'span 2' }}>Create Student Account</button>
                        </form>
                    </div>
                )}

                {showForm && activeTab === 'subjects' && (
                    <div className="card" style={{ marginBottom: '2rem' }}>
                        <form onSubmit={handleAddSubject} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                            <input placeholder="Subject Name" value={newSubject.name} onChange={e => setNewSubject({...newSubject, name: e.target.value})} required />
                            <input placeholder="Credits" type="number" value={newSubject.credits} onChange={e => setNewSubject({...newSubject, credits: e.target.value})} required />
                            <input placeholder="Semester" type="number" value={newSubject.semester} onChange={e => setNewSubject({...newSubject, semester: e.target.value})} required />
                            <button type="submit" className="btn-primary" style={{ gridColumn: 'span 2' }}>Add Subject</button>
                        </form>
                    </div>
                )}

                <div className="card">
                    {activeTab === 'students' ? (
                        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                            <thead>
                                <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)' }}>
                                    <th style={{ padding: '1rem' }}>Name</th>
                                    <th style={{ padding: '1rem' }}>Branch</th>
                                    <th style={{ padding: '1rem' }}>Semester</th>
                                    <th style={{ padding: '1rem' }}>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {loading ? (
                                    <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>Loading students...</td></tr>
                                ) : students.length === 0 ? (
                                    <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>No students found</td></tr>
                                ) : (
                                    students.map(s => (
                                        <tr key={s.id} style={{ borderBottom: '1px solid var(--border)' }}>
                                            <td style={{ padding: '1rem' }}>{s.name}</td>
                                            <td style={{ padding: '1rem' }}>{s.branch}</td>
                                            <td style={{ padding: '1rem' }}>{s.currentSemester}</td>
                                            <td style={{ padding: '1rem' }}>
                                                <button onClick={() => handleDeleteStudent(s.id)} style={{ color: 'var(--error)', background: 'none', border: 'none', cursor: 'pointer' }}>Delete</button>
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    ) : (
                        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                            <thead>
                                <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)' }}>
                                    <th style={{ padding: '1rem' }}>Subject Name</th>
                                    <th style={{ padding: '1rem' }}>Credits</th>
                                    <th style={{ padding: '1rem' }}>Semester</th>
                                    <th style={{ padding: '1rem' }}>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {loading ? (
                                    <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>Loading subjects...</td></tr>
                                ) : subjects.length === 0 ? (
                                    <tr><td colSpan="4" style={{ padding: '2rem', textAlign: 'center' }}>No subjects found</td></tr>
                                ) : (
                                    subjects.map(sub => (
                                        <tr key={sub.id} style={{ borderBottom: '1px solid var(--border)' }}>
                                            <td style={{ padding: '1rem' }}>{sub.name}</td>
                                            <td style={{ padding: '1rem' }}>{sub.credits}</td>
                                            <td style={{ padding: '1rem' }}>{sub.semester}</td>
                                            <td style={{ padding: '1rem' }}>
                                                <button onClick={() => handleDeleteSubject(sub.id)} style={{ color: 'var(--error)', background: 'none', border: 'none', cursor: 'pointer' }}>Delete</button>
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    )}
                </div>
            </div>
        </div>
    );
};

export default AdminDashboard;
