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
} from '@mui/material';
import { Person, Email, Home, Badge, Cake, Save } from '@mui/icons-material';
import { apiService } from '../../services/api';
import { ClientRequest } from '../../types';

const AddClientForm: React.FC = () => {
  const [formData, setFormData] = useState<ClientRequest>({
    nom: '',
    prenom: '',
    numeroIdentite: '',
    dateNaissance: '',
    email: '',
    adressePostale: '',
  });

  const [errors, setErrors] = useState<Partial<ClientRequest>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const validateForm = (): boolean => {
    const newErrors: Partial<ClientRequest> = {};

    if (!formData.nom.trim()) {
      newErrors.nom = 'Le nom est obligatoire';
    }

    if (!formData.prenom.trim()) {
      newErrors.prenom = 'Le prénom est obligatoire';
    }

    if (!formData.numeroIdentite.trim()) {
      newErrors.numeroIdentite = 'Le numéro d\'identité est obligatoire';
    }

    if (!formData.dateNaissance) {
      newErrors.dateNaissance = 'La date de naissance est obligatoire';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'L\'email est obligatoire';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Format d\'email invalide';
    }

    if (!formData.adressePostale.trim()) {
      newErrors.adressePostale = 'L\'adresse postale est obligatoire';
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
    if (errors[name as keyof ClientRequest]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined,
      }));
    }

    // Clear messages
    if (successMessage) setSuccessMessage('');
    if (errorMessage) setErrorMessage('');
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
      const response = await apiService.createClient(formData);
      setSuccessMessage(`Client créé avec succès! Login: ${response.login}, Mot de passe envoyé par email.`);
      setFormData({
        nom: '',
        prenom: '',
        numeroIdentite: '',
        dateNaissance: '',
        email: '',
        adressePostale: '',
      });
    } catch (err: any) {
      setErrorMessage(err.response?.data?.error || err.message || 'Erreur lors de la création du client');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box sx={{ maxWidth: 800, mx: 'auto', p: 3 }}>
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
              Ajouter un nouveau client
            </Typography>
            <Typography variant="subtitle1" color="text.secondary">
              Remplissez les informations du client pour créer un nouveau compte
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
            <Box sx={{ display: 'flex', gap: 3, mb: 3 }}>
              <TextField
                fullWidth
                label="Nom"
                name="nom"
                value={formData.nom}
                onChange={handleChange}
                error={!!errors.nom}
                helperText={errors.nom}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Person color="primary" />
                    </InputAdornment>
                  ),
                }}
              />

              <TextField
                fullWidth
                label="Prénom"
                name="prenom"
                value={formData.prenom}
                onChange={handleChange}
                error={!!errors.prenom}
                helperText={errors.prenom}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Person color="primary" />
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ display: 'flex', gap: 3, mb: 3 }}>
              <TextField
                fullWidth
                label="Numéro d'identité"
                name="numeroIdentite"
                value={formData.numeroIdentite}
                onChange={handleChange}
                error={!!errors.numeroIdentite}
                helperText={errors.numeroIdentite}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Badge color="primary" />
                    </InputAdornment>
                  ),
                }}
              />

              <TextField
                fullWidth
                label="Date de naissance"
                name="dateNaissance"
                type="date"
                value={formData.dateNaissance}
                onChange={handleChange}
                error={!!errors.dateNaissance}
                helperText={errors.dateNaissance}
                disabled={isLoading}
                InputLabelProps={{
                  shrink: true,
                }}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Cake color="primary" />
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ mb: 3 }}>
              <TextField
                fullWidth
                label="Adresse email"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                error={!!errors.email}
                helperText={errors.email}
                disabled={isLoading}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Email color="primary" />
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ mb: 4 }}>
              <TextField
                fullWidth
                label="Adresse postale"
                name="adressePostale"
                value={formData.adressePostale}
                onChange={handleChange}
                error={!!errors.adressePostale}
                helperText={errors.adressePostale}
                disabled={isLoading}
                multiline
                rows={3}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Home color="primary" />
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
                {isLoading ? 'Création en cours...' : 'Créer le client'}
              </Button>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
};

export default AddClientForm; 