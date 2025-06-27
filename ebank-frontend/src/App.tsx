import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import LoginForm from './components/Login/LoginForm';
import Layout from './components/Layout/Layout';
import Dashboard from './components/Dashboard/Dashboard';
import TransferForm from './components/Transfer/TransferForm';
import AddClientForm from './components/Client/AddClientForm';
import AddAccountForm from './components/Account/AddAccountForm';
import ChangePasswordForm from './components/Auth/ChangePasswordForm';
import { UserRole } from './types';
import { Box, CircularProgress } from '@mui/material';

// Create theme
const theme = createTheme({
  palette: {
    primary: {
      main: '#667eea',
    },
    secondary: {
      main: '#764ba2',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
        },
      },
    },
  },
});

// Protected Route Component
interface ProtectedRouteProps {
  children: React.ReactNode;
  allowedRoles?: UserRole[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children, allowedRoles }) => {
  const { user, isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (!isAuthenticated || !user) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(user.role)) {
    return (
      <Layout>
        <Box textAlign="center" mt={4}>
          <h2>Accès refusé</h2>
          <p>Vous n'avez pas le droit d'accéder à cette fonctionnalité. Veuillez contacter votre administrateur.</p>
        </Box>
      </Layout>
    );
  }

  return <Layout>{children}</Layout>;
};

// Home component that redirects based on role
const Home: React.FC = () => {
  const { user } = useAuth();

  if (user?.role === UserRole.CLIENT) {
    return <Navigate to="/dashboard" replace />;
  } else if (user?.role === UserRole.AGENT_GUICHET) {
    return <Navigate to="/add-client" replace />;
  }

  return <Navigate to="/login" replace />;
};

// Main App Routes
const AppRoutes: React.FC = () => {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Routes>
      {/* Public routes */}
      <Route 
        path="/login" 
        element={
          isAuthenticated ? <Navigate to="/" replace /> : <LoginForm />
        } 
      />

      {/* Protected routes */}
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        }
      />

      {/* Client routes */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute allowedRoles={[UserRole.CLIENT]}>
            <Dashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/transfer"
        element={
          <ProtectedRoute allowedRoles={[UserRole.CLIENT]}>
            <TransferForm />
          </ProtectedRoute>
        }
      />

      {/* Agent routes */}
      <Route
        path="/add-client"
        element={
          <ProtectedRoute allowedRoles={[UserRole.AGENT_GUICHET]}>
            <AddClientForm />
          </ProtectedRoute>
        }
      />

      <Route
        path="/add-account"
        element={
          <ProtectedRoute allowedRoles={[UserRole.AGENT_GUICHET]}>
            <AddAccountForm />
          </ProtectedRoute>
        }
      />

      {/* Common routes */}
      <Route
        path="/change-password"
        element={
          <ProtectedRoute>
            <ChangePasswordForm />
          </ProtectedRoute>
        }
      />

      {/* Catch all route */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

// Main App Component
const App: React.FC = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <Router>
          <AppRoutes />
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
};

export default App;
