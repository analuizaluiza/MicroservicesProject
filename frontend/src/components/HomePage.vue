<template>
  <div class="home-page">
    <h1>Bem-vindo ao Sistema Escolar</h1>
    <p class="subtitle">Gerencie escolas e estudantes de forma simples e eficiente</p>
    <div class="options">
      <router-link to="/students" class="btn btn-primary">Gerenciar Alunos</router-link>
      <router-link to="/schools" class="btn btn-primary">Gerenciar Escolas</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>

<style scoped>
.home-page {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  background: linear-gradient(135deg, #143642, #121212); 
  color: #e0e0e0;
  font-family: 'Arial', sans-serif;
  text-align: center;
  padding: 20px;
}

h1 {
  font-size: 3.5rem;
  margin-bottom: 20px;
  color: #B3EDEC;
  font-weight: bold;
  text-shadow: 2px 2px 10px rgba(33, 43, 11, 0.8);
}

.subtitle {
  color: #9e9e9e;
  font-size: 1.3rem;
  margin-bottom: 40px;
}

.options {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 20px;
}

.btn {
  padding: 16px 32px;
  font-size: 18px;
  font-weight: bold;
  border-radius: 12px;
  text-decoration: none;
  text-align: center;
  color: #ffffff;
  transition: all 0.3s ease; 
  box-shadow: 0 4px 8px rgba(33, 43, 11, 0.8);   
}

.btn-primary {
  background-color: #008C95;
}

.btn-primary:hover {
  background-color: #005B68;
  transform: translateY(-2px);  
}

/* Responsividade para telas menores */
@media (max-width: 768px) {
  h1 {
    font-size: 2.5rem;
  }
  
  .options {
    flex-direction: column;
    gap: 15px;
  }
  
  .btn {
    padding: 14px 28px;
    font-size: 16px;
  }
}
</style>
