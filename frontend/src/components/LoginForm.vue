<template>
  <div class="login-container">
    <div class="login-box">
      <h2>Login</h2>
      <form @submit.prevent="handleSubmit">
        <div class="input-group">
          <label for="username">Usuário</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="input-group">
          <label for="password">Senha</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <button type="submit">Entrar</button>
      </form>
      <div class="signup-link">
        <p>Ainda não tem uma conta? <router-link to="/signup" class="login-link">Cadastre-se</router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Certifique-se de que axios está instalado

export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axios.post('http://localhost:8080/api/users/login', {
          usuario: this.username,
          senha: this.password
        });

        if (response.status === 200) {
          this.$router.push('/');  // Redireciona para o dashboard
        } else {
          alert('Credenciais inválidas');
        }
      } catch (error) {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Tente novamente.');
      }
    }

  }
};
</script>

<style scoped>
/* Container de fundo */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #143642, #48B6BD); 
  color: #fff;
}

.login-box {
  background: rgba(33, 33, 33, 0.9); /* Transparência para efeito de sobreposição */
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
  font-weight: bold;
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
  transition: color 0.3s;
}

input {
  width: 100%;
  padding: 14px;
  border: 2px solid #444;
  border-radius: 8px;
  background: #222;
  color: #fff;
  font-size: 16px;
  transition: border-color 0.3s ease;
  
}

input:focus {
  border-color: #00bcd4;
  outline: none;
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

.signup-link {
  margin-top: 20px;
  font-size: 14px;
  text-align: center;
}

.signup-link a {
  color: #48B6BD;
  text-decoration: none;
  transition: color 0.3s ease;
}

.signup-link a:hover {
  color: #00a3b2;
  text-decoration: underline;
}

/* Animação suave nas transições */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.login-box {
  animation: fadeIn 1s ease-out;
}
</style>
