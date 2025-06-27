import React, { useState } from 'react';
import {
  Box,
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  ListItemButton,
  Divider,
  Avatar,
  Menu,
  MenuItem,
  useTheme,
  useMediaQuery,
} from '@mui/material';
import {
  Menu as MenuIcon,
  Dashboard,
  PersonAdd,
  AccountBalance,
  Send,
  Lock,
  Logout,
  Home,
} from '@mui/icons-material';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { UserRole } from '../../types';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));

  const [drawerOpen, setDrawerOpen] = useState(!isMobile);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const handleDrawerToggle = () => {
    setDrawerOpen(!drawerOpen);
  };

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleProfileMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
    handleProfileMenuClose();
  };

  const handleChangePassword = () => {
    navigate('/change-password');
    handleProfileMenuClose();
  };

  // Menu items based on user role
  const getMenuItems = () => {
    if (!user) return [];

    const commonItems = [
      { text: 'Accueil', icon: <Home />, path: '/' },
    ];

    if (user.role === UserRole.CLIENT) {
      return [
        ...commonItems,
        { text: 'Tableau de bord', icon: <Dashboard />, path: '/dashboard' },
        { text: 'Nouveau virement', icon: <Send />, path: '/transfer' },
      ];
    }

    if (user.role === UserRole.AGENT_GUICHET) {
      return [
        ...commonItems,
        { text: 'Ajouter client', icon: <PersonAdd />, path: '/add-client' },
        { text: 'Nouveau compte', icon: <AccountBalance />, path: '/add-account' },
      ];
    }

    return commonItems;
  };

  const menuItems = getMenuItems();

  const drawerContent = (
    <Box sx={{ width: 250, pt: 2 }}>
      <Box sx={{ p: 2, textAlign: 'center' }}>
        <Typography variant="h6" sx={{ fontWeight: 'bold', color: 'primary.main' }}>
          eBank
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {user?.role === UserRole.CLIENT ? 'Espace Client' : 'Espace Agent'}
        </Typography>
      </Box>
      <Divider />
      <List>
        {menuItems.map((item) => (
          <ListItem key={item.text} disablePadding>
            <ListItemButton
              selected={location.pathname === item.path}
              onClick={() => {
                navigate(item.path);
                if (isMobile) setDrawerOpen(false);
              }}
              sx={{
                '&.Mui-selected': {
                  backgroundColor: 'primary.light',
                  '&:hover': {
                    backgroundColor: 'primary.light',
                  },
                },
              }}
            >
              <ListItemIcon sx={{ color: location.pathname === item.path ? 'primary.main' : 'inherit' }}>
                {item.icon}
              </ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar
        position="fixed"
        sx={{
          width: { md: `calc(100% - ${drawerOpen ? 250 : 0}px)` },
          ml: { md: `${drawerOpen ? 250 : 0}px` },
          background: 'linear-gradient(45deg, #667eea, #764ba2)',
        }}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          
          <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            {user?.role === UserRole.CLIENT ? 'Tableau de bord' : 'Administration'}
          </Typography>

          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            <Typography variant="body2">
              {user?.prenom} {user?.nom}
            </Typography>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleProfileMenuOpen}
              color="inherit"
            >
              <Avatar sx={{ bgcolor: 'secondary.main' }}>
                {user?.prenom?.charAt(0)}{user?.nom?.charAt(0)}
              </Avatar>
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>

      <Box
        component="nav"
        sx={{ width: { md: drawerOpen ? 250 : 0 }, flexShrink: { md: 0 } }}
      >
        <Drawer
          variant={isMobile ? "temporary" : "persistent"}
          open={drawerOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            '& .MuiDrawer-paper': {
              boxSizing: 'border-box',
              width: 250,
            },
          }}
        >
          {drawerContent}
        </Drawer>
      </Box>

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: { md: `calc(100% - ${drawerOpen ? 250 : 0}px)` },
          mt: 8,
          backgroundColor: '#f5f5f5',
          minHeight: 'calc(100vh - 64px)',
        }}
      >
        {children}
      </Box>

      <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={Boolean(anchorEl)}
        onClose={handleProfileMenuClose}
      >
        <MenuItem onClick={handleChangePassword}>
          <ListItemIcon>
            <Lock fontSize="small" />
          </ListItemIcon>
          <ListItemText>Changer mot de passe</ListItemText>
        </MenuItem>
        <MenuItem onClick={handleLogout}>
          <ListItemIcon>
            <Logout fontSize="small" />
          </ListItemIcon>
          <ListItemText>Déconnexion</ListItemText>
        </MenuItem>
      </Menu>
    </Box>
  );
};

export default Layout; 