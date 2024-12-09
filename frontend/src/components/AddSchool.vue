<template>
  <div class="add-school">
    
    <form @submit.prevent="addSchool" class="form">
      <label>Nome da Escola:
        <input
          v-model="school.schoolName"
          type="text"
          placeholder="Digite o nome da escola"
          required
        />
      </label>

      <label>Localização:
        <input
          v-model="school.location"
          type="text"
          placeholder="Digite a localização"
          required
        />
      </label>

      <label>Nome do Diretor(a):
        <input
          v-model="school.principalName"
          type="text"
          placeholder="Digite o nome do(a) diretor(a)"
          required
        />
      </label>

      <div class="actions">
        <button type="submit" class="btn btn-primary">Salvar</button>
        <button type="reset" @click="resetForm" class="btn btn-secondary">Limpar</button>
      </div>
    </form>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      school: {
        schoolName: '',
        location: '',
        principalName: '',
      },
    };
  },
  methods: {
    resetForm() {
    this.school = { schoolName: '', location: '', principalName: '' };
    },
    async addSchool() {
      try {
        await axios.post('http://localhost:8080/api/schools', this.school);
        alert('Escola adicionada com sucesso!');
        this.school = { schoolName: '', location: '', principalName: '' };
        this.$emit('school-added');
      } catch (error) {
        console.error('Erro ao adicionar escola:', error);
        alert('Erro ao adicionar escola');
      }
    },
  },
};
</script>

<style scoped>
.add-school {
  font-family: Arial, sans-serif;
  background-color: #121212;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 8px;
  max-width: 600px;
  margin: auto;
}

h1 {
  text-align: center;
  color: #ffffff;
  margin-bottom: 20px;
}

.form label {
  display: block;
  margin-bottom: 15px;
  font-weight: bold;
}

.form input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 4px;
  color: #e0e0e0;
}

.form input::placeholder {
  color: #aaa;
}

.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
}

.btn-secondary {
  background-color: #6c757d;
  color: #fff;
}

.btn:hover {
  opacity: 0.9;
}
</style>

