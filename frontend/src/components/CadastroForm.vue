<template>
  <div class="signup-container">
    <div class="signup-box">
      <h2>Cadastro</h2>
      <form @submit.prevent="addUser">
        <div class="input-group">
          <label for="name">Nome Completo</label>
          <input type="text" id="name" v-model="user.name" required />
        </div>
        <div class="input-group">
          <label for="usuario">Usuário</label>
          <input type="text" id="user" v-model="user.username" required />
        </div>
        <div class="input-group">
          <label for="password">Senha</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="input-group">
          <label for="confirmPassword">Confirmar Senha</label>
          <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
        </div>
        <div class="d-flex justify-content-end">
          <button type="submit">Cadastrar</button>
        </div>
      </form>
      <div class="login-link">
        <p>Já tem uma conta? <router-link to="/login">Faça login</router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
  return {
    user: {
      name: '',
      username: '',
      password: '',
      confirmPassword: '',
    },
    isLoading: false,  // Estado de carregamento
  };
},
methods: {
  async addUser() {
    if (this.user.password !== this.user.confirmPassword) {
      alert("As senhas não coincidem.");
      return;
    }

    this.isLoading = true;  // Ativa o carregamento

    try {
        const response = await axios.post('http://localhost:8080/api/users/signup', {
        nome: this.user.name,
        usuario: this.user.username,
        senha: this.user.password,
      });

      if (response.status === 201) {
        alert('Usuário cadastrado com sucesso!');
        this.user = { name: '', username: '', password: '', confirmPassword: '' };
        this.$router.push('/login');
      }
    } catch (error) {
      console.error('Erro ao cadastrar usuário:', error);
      alert('Erro ao cadastrar usuário. Tente novamente mais tarde.');
    } finally {
      this.isLoading = false;  // Desativa o carregamento
    }
  },
},
};
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #143642, #48B6BD);
  color: #fff;
}

.signup-box {
  background: rgba(33, 33, 33, 0.9);
  padding: 40px 50px;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 500px;
  text-align: center;
}

h2 {
  font-size: 32px;
  color: #fff;
  margin-bottom: 30px;
}

.input-group {
  margin-bottom: 25px;
  text-align: left;
}

label {
  display: block;
  font-size: 14px;
  color: #ddd;
  margin-bottom: 8px;
}

input {
  width: 100%;
  padding: 14px;
  border: 2px solid #444;
  border-radius: 8px;
  background: #222;
  color: #fff;
  font-size: 16px;
}

button {
  width: 100%;
  padding: 14px;
  background: #008C95;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 18px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background: #005B68;
  transform: translateY(-1px);
}

button:active {
  transform: translateY(2px);
}

.login-link {
  margin-top: 20px;
  font-size: 14px;
  text-align: center;
}

.login-link a {
  color: #48B6BD;
  text-decoration: none;
}

.login-link a:hover {
  color: #00a3b2;
  text-decoration: underline;
}
</style>
