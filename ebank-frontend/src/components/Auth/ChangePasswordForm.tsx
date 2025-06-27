import React, { useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Alert,
  CircularProgress,
  InputAdornment,
  IconButton,
} from '@mui/material';
import { Visibility, VisibilityOff, Lock, Save } from '@mui/icons-material';
import { apiService } from '../../services/api';
import { ChangePasswordRequest } from '../../types';

const ChangePasswordForm: React.FC = () => {
  const [formData, setFormData] = useState<ChangePasswordRequest>({
    ancienMotDePasse: '',
    nouveauMotDePasse: '',
    confirmationMotDePasse: '',
  });

  const [showPasswords, setShowPasswords] = useState({
    ancien: false,
    nouveau: false,
    confirmation: false,
  });

  const [errors, setErrors] = useState<Partial<ChangePasswordRequest>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const validateForm = (): boolean => {
    const newErrors: Partial<ChangePasswordRequest> = {};

    if (!formData.ancienMotDePasse.trim()) {
      newErrors.ancienMotDePasse = 'L\'ancien mot de passe est obligatoire';
    }

    if (!formData.nouveauMotDePasse.trim()) {
      newErrors.nouveauMotDePasse = 'Le nouveau mot de passe est obligatoire';
    } else if (formData.nouveauMotDePasse.length < 6) {
      newErrors.nouveauMotDePasse = 'Le mot de passe doit contenir au moins 6 caractères';
    }

    if (!formData.confirmationMotDePasse.trim()) {
      newErrors.confirmationMotDePasse = 'La confirmation du mot de passe est obligatoire';
    } else if (formData.nouveauMotDePasse !== formData.confirmationMotDePasse) {
      newErrors.confirmationMotDePasse = 'La confirmation ne correspond pas au nouveau mot de passe';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));

    // Clear error when user starts typing
    if (errors[name as keyof ChangePasswordRequest]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined,
      }));
    }

    // Clear messages
    if (successMessage) setSuccessMessage('');
    if (errorMessage) setErrorMessage('');
  };

  const togglePasswordVisibility = (field: keyof typeof showPasswords) => {
    setShowPasswords(prev => ({
      ...prev,
      [field]: !prev[field],
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setIsLoading(true);
    setErrorMessage('');
    setSuccessMessage('');

    try {
      await apiService.changePassword(formData);
      setSuccessMessage('Mot de passe modifié avec succès!');
      setFormData({
        ancienMotDePasse: '',
        nouveauMotDePasse: '',
        confirmationMotDePasse: '',
      });
    } catch (err: any) {
      setErrorMessage(err.response?.data?.error || err.message || 'Erreur lors du changement de mot de passe');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box sx={{ maxWidth: 500, mx: 'auto', p: 3 }}>
      <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
        <CardContent sx={{ p: 4 }}>
          <Box textAlign="center" mb={4}>
            <Typography
              variant="h4"
              component="h1"
              gutterBottom
              sx={{
                fontWeight: 'bold',
                color: '#333',
                background: 'linear-gradient(45deg, #667eea, #764ba2)',
                backgroundClip: 'text',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
              }}
            >
              Changer le mot de passe
            </Typography>
            <Typography variant="subtitle1" color="text.secondary">
              Modifiez votre mot de passe pour sécuriser votre compte
            </Typography>
          </Box>

          {successMessage && (
            <Alert severity="success" sx={{ mb: 3 }}>
              {successMessage}
            </Alert>
          )}

          {errorMessage && (
            <Alert severity="error" sx={{ mb: 3 }}>
              {errorMessage}
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <Box sx={{ mb: 3 }}>
              <TextField
                fullWidth
                label="Ancien mot de passe"
                name="ancienMotDePasse"
                type={showPasswords.ancien ? 'text' : 'password'}
                value={formData.ancienMotDePasse}
                onChange={handleChange}
                error={!!errors.ancienMotDePasse}
                helperText={errors.ancienMotDePasse}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Lock color="primary" />
                    </InputAdornment>
                  ),
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() => togglePasswordVisibility('ancien')}
                        edge="end"
                      >
                        {showPasswords.ancien ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ mb: 3 }}>
              <TextField
                fullWidth
                label="Nouveau mot de passe"
                name="nouveauMotDePasse"
                type={showPasswords.nouveau ? 'text' : 'password'}
                value={formData.nouveauMotDePasse}
                onChange={handleChange}
                error={!!errors.nouveauMotDePasse}
                helperText={errors.nouveauMotDePasse}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Lock color="primary" />
                    </InputAdornment>
                  ),
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() => togglePasswordVisibility('nouveau')}
                        edge="end"
                      >
                        {showPasswords.nouveau ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ mb: 4 }}>
              <TextField
                fullWidth
                label="Confirmation du nouveau mot de passe"
                name="confirmationMotDePasse"
                type={showPasswords.confirmation ? 'text' : 'password'}
                value={formData.confirmationMotDePasse}
                onChange={handleChange}
                error={!!errors.confirmationMotDePasse}
                helperText={errors.confirmationMotDePasse}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Lock color="primary" />
                    </InputAdornment>
                  ),
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() => togglePasswordVisibility('confirmation')}
                        edge="end"
                      >
                        {showPasswords.confirmation ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box textAlign="center">
              <Button
                type="submit"
                variant="contained"
                size="large"
                disabled={isLoading}
                startIcon={isLoading ? <CircularProgress size={20} /> : <Save />}
                sx={{
                  px: 4,
                  py: 1.5,
                  background: 'linear-gradient(45deg, #667eea, #764ba2)',
                  '&:hover': {
                    background: 'linear-gradient(45deg, #5a6fd8, #6a4190)',
                  },
                }}
              >
                {isLoading ? 'Modification en cours...' : 'Modifier le mot de passe'}
              </Button>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
};

export default ChangePasswordForm; 