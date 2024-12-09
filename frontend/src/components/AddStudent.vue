<template>
  <div class="add-student">
    <h1>Adicionar Estudante</h1>
    <form @submit.prevent="addStudent" class="form">
      <label>Nome:
        <input v-model="student.name" type="text" placeholder="Digite o nome" required />
      </label>

      <label>Idade:
        <input v-model="student.age" type="number" placeholder="Digite a idade" required />
      </label>

      <label>Gênero:
        <select v-model="student.gender" required>
          <option value="" disabled>Selecione o gênero</option>
          <option value="Masculino">Masculino</option>
          <option value="Feminino">Feminino</option>
          <option value="Outro">Outro</option>
          <option value="Prefiro não informar">Prefiro não informar</option>
        </select>
      </label>

      <label>Escola:
        <select v-model="student.schoolId" required>
          <option value="" disabled>Selecione a escola</option>
          <option v-for="school in schools" :key="school.id" :value="school.id">
            {{ school.schoolName }}
          </option>
        </select>
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
            student: {
                name: '',
                age: null,
                gender: '',
                schoolId: ''
            },
            schools: []
        };
    },
    created() {
        this.fetchSchools();
    },
    methods: {
        resetForm() {
            this.student = { name: '', age: null, gender: '', schoolId: '' };
        },
        async fetchSchools() {
            try {
                const response = await axios.get('http://localhost:8080/api/schools');
                this.schools = response.data;
            } catch (error) {
                console.error('Erro ao buscar escolas:', error);
                alert('Erro ao carregar lista de escolas');
            }
        },
        async addStudent() {
            try {
                await axios.post('http://localhost:8080/api/students', this.student);
                alert('Estudante adicionado com sucesso!');
                this.student = { name: '', age: null, gender: '', schoolId: '' };
                this.$emit('student-added');
            } catch (error) {
                console.error('Erro ao adicionar estudante:', error);
                alert('Erro ao adicionar estudante');
            }
        }
    }
};
</script>

<style>
.add-student {
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

.form input,
.form select {
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

