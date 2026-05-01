import React, { useState, useEffect } from 'react';
import api from '../services/api';

const FacultyDashboard = () => {
    const [students, setStudents] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState('');
    const [selectedSubject, setSelectedSubject] = useState('');
    const [marks, setMarks] = useState('');
    const [semester, setSemester] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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
            setError("Failed to load students or subjects. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleSubmitMarks = async (e) => {
        e.preventDefault();
        try {
            await api.post('/marks', {
                studentId: selectedStudent,
                subjectId: selectedSubject,
                marksObtained: parseInt(marks),
                semester: parseInt(semester)
            });
            setError('Marks updated successfully!'); // Using setError as a feedback mechanism as requested to avoid alerts
            setMarks('');
        } catch (err) {
            setError('Error updating marks. Please check if data is valid.');
        }
    };

    return (
        <div className="dashboard-layout">
            <div className="sidebar">
                <h2 style={{ color: 'var(--primary)', marginBottom: '2rem' }}>EduResult</h2>
                <nav>
                    <div style={{ color: 'var(--primary)', fontWeight: 'bold', marginBottom: '1rem' }}>Faculty Panel</div>
                    <div style={{ color: 'var(--text-muted)', marginBottom: '1rem' }}>Enter Marks</div>
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
                    <div style={{ 
                        background: error.includes('successfully') ? '#d1fae5' : '#fee2e2', 
                        color: error.includes('successfully') ? '#065f46' : '#991b1b', 
                        padding: '1rem', borderRadius: '0.5rem', marginBottom: '1rem', 
                        border: `1px solid ${error.includes('successfully') ? '#a7f3d0' : '#fecaca'}` 
                    }}>
                        {error}
                    </div>
                )}

                <h1>Marks Entry</h1>
                <p style={{ color: 'var(--text-muted)', marginBottom: '2rem' }}>Update student marks and automate result generation.</p>

                {loading ? (
                    <div className="card" style={{ textAlign: 'center', padding: '3rem' }}>
                        Loading form data...
                    </div>
                ) : (
                    <div className="card" style={{ maxWidth: '600px' }}>
                        <form onSubmit={handleSubmitMarks}>
                            <div style={{ marginBottom: '1.25rem' }}>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Select Student</label>
                                <select 
                                    style={{ width: '100%', padding: '0.75rem', background: 'var(--bg-dark)', color: 'white', border: '1px solid var(--border)', borderRadius: '0.5rem' }}
                                    value={selectedStudent}
                                    onChange={e => setSelectedStudent(e.target.value)}
                                    required
                                >
                                    <option value="">{students.length === 0 ? "No students found" : "Choose a student..."}</option>
                                    {students.map(s => <option key={s.id} value={s.id}>{s.name} ({s.branch})</option>)}
                                </select>
                            </div>

                            <div style={{ marginBottom: '1.25rem' }}>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Select Subject</label>
                                <select 
                                    style={{ width: '100%', padding: '0.75rem', background: 'var(--bg-dark)', color: 'white', border: '1px solid var(--border)', borderRadius: '0.5rem' }}
                                    value={selectedSubject}
                                    onChange={e => setSelectedSubject(e.target.value)}
                                    required
                                >
                                    <option value="">{subjects.length === 0 ? "No subjects found" : "Choose a subject..."}</option>
                                    {subjects.map(sub => <option key={sub.id} value={sub.id}>{sub.name}</option>)}
                                </select>
                            </div>

                            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', marginBottom: '2rem' }}>
                                <div>
                                    <label style={{ display: 'block', marginBottom: '0.5rem' }}>Semester</label>
                                    <input type="number" value={semester} onChange={e => setSemester(e.target.value)} required min="1" max="8" />
                                </div>
                                <div>
                                    <label style={{ display: 'block', marginBottom: '0.5rem' }}>Marks (0-100)</label>
                                    <input type="number" value={marks} onChange={e => setMarks(e.target.value)} required min="0" max="100" />
                                </div>
                            </div>

                            <button type="submit" className="btn-primary" style={{ width: '100%' }}>Submit Marks</button>
                        </form>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FacultyDashboard;
