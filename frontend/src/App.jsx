import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import AdminDashboard from './pages/AdminDashboard';
import FacultyDashboard from './pages/FacultyDashboard';
import StudentDashboard from './pages/StudentDashboard';

const PrivateRoute = ({ children, role }) => {
    const { user } = useAuth();
    if (!user) return <Navigate to="/login" />;
    if (role && user.role !== role) return <Navigate to="/" />;
    return children;
};

const AppRoutes = () => {
    const { user } = useAuth();

    return (
        <Routes>
            <Route path="/login" element={!user ? <LoginPage /> : <Navigate to="/" />} />
            
            <Route path="/admin" element={
                <PrivateRoute role="ROLE_ADMIN">
                    <AdminDashboard />
                </PrivateRoute>
            } />
            
            <Route path="/faculty" element={
                <PrivateRoute role="ROLE_FACULTY">
                    <FacultyDashboard />
                </PrivateRoute>
            } />
            
            <Route path="/student" element={
                <PrivateRoute role="ROLE_STUDENT">
                    <StudentDashboard />
                </PrivateRoute>
            } />

            <Route path="/" element={
                user ? (
                    user.role === 'ROLE_ADMIN' ? <Navigate to="/admin" /> :
                    user.role === 'ROLE_FACULTY' ? <Navigate to="/faculty" /> :
                    <Navigate to="/student" />
                ) : <Navigate to="/login" />
            } />
        </Routes>
    );
};

function App() {
    return (
        <AuthProvider>
            <Router>
                <AppRoutes />
            </Router>
        </AuthProvider>
    );
}

export default App;
