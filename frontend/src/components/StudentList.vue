<template>
  <div class="dashboard-container py-5">
   <a @click="goBack" href="#" class="btn-link-home">Página Inicial</a>
    <!-- Cabeçalho -->
    <header class="text-center mb-5">
      <h1 class="dashboard-title">Gerenciar Alunos</h1>
    </header>

    <h2 class="section-title">Lista de Estudantes</h2>

    <div class="header"> 
      <div class="filter">
        <label for="schoolFilter">Filtrar por Escola:</label>
        <select 
          v-model="selectedSchoolId" 
          @change="filterStudentsBySchool" 
          id="schoolFilter"
        >
          <option value="">Todas as Escolas</option>
          <option 
            v-for="school in schoolsList" 
            :value="school.id" 
            :key="school.id"
          >
            {{ school.schoolName }}
          </option>
        </select>
      </div>
      <div class="controls">
        <button @click="openAddStudentModal" class="btn btn-primary"> Adicionar Estudante </button>
      </div>
    </div>

      <!-- Tabela de Alunos -->
    <table class="table">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Idade</th>
          <th>Gênero</th>
          <th>Escola</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="student in filteredStudents"
          :key="student.id"
          @click="selectStudent(student)"
          :class="{ 'selected': selectedStudent?.id === student.id }"
        >
          <td>{{ student.name }}</td>
          <td>{{ student.age }}</td>
          <td>{{ student.gender || 'Não informado' }}</td>
          <td>{{ student.schoolName }}</td>
          <td>
            <div class="button-group">
              <button @click.stop="startEdit(student)" class="btn btn-secondary-action">Editar</button>
              <button @click.stop="confirmDelete(student)" class="btn btn-danger-action">Excluir</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

      <!-- Modal de Edição / Adição -->
      <div v-if="editingStudent || addingStudent" class="modal">
        <div class="modal-content">
          <h2>{{ addingStudent ? 'Adicionar Estudante' : 'Editar Estudante' }}</h2>
          <form @submit.prevent="saveStudent">
            <label>Nome:
              <input v-model="currentStudent.name" required />
            </label>
            <label>Idade:
              <input type="number" v-model="currentStudent.age" required />
            </label>
            <label>Gênero:
              <select v-model="currentStudent.gender" required>
                <option v-for="option in genderOptions" :value="option" :key="option">{{ option }}</option>
              </select>
            </label>
            <label>Escola:
              <select v-model="currentStudent.schoolId" required>
                <option v-for="school in schoolsList" :value="school.id" :key="school.id">
                  {{ school.schoolName }}
                </option>
              </select>
            </label>
            <div class="actions">
              <button type="submit" class="btn btn-primary">Salvar</button>
              <button type="button" @click="cancelEdit" class="btn btn-secondary">Cancelar</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      students: [],
      schoolsList: [], 
      selectedStudent: null,
      editingStudent: null,
      addingStudent: false,
      currentStudent: {}, 
      genderOptions: ['Masculino', 'Feminino', 'Outro', 'Prefiro não informar'],
      selectedSchoolId: ""
    };
  },
  computed: {
    studentsWithSchoolNames() {
      return this.students.map(student => ({
        ...student,
        schoolName: this.schoolsList.find(school => school.id === student.schoolId)?.schoolName || 'Escola não encontrada'
      }));
    },
    // Computed para filtrar estudantes conforme a escola selecionada
    filteredStudents() {
      if (!this.selectedSchoolId) {
        return this.studentsWithSchoolNames; 
      }
      return this.studentsWithSchoolNames.filter(student => student.schoolId === this.selectedSchoolId);
    }
  },
  created() {
    this.fetchStudents();
    this.fetchAllSchools(); 
  },
  methods: {
    async fetchStudents() {
      try {
        const response = await axios.get('http://localhost:8080/api/students');
        this.students = response.data;
        this.students = this.students.map(student => ({
          ...student,
          gender: student.gender || ''
        }));
      } catch (error) {
        console.error('Erro ao buscar estudantes:', error);
      }
    },
    async fetchAllSchools() {
      try {
        const response = await axios.get('http://localhost:8080/api/schools');
        this.schoolsList = response.data; // Preenche a lista de escolas com dados do banco
      } catch (error) {
        console.error('Erro ao buscar escolas:', error);
      }
    },
    selectStudent(student) {
      this.selectedStudent = this.selectedStudent?.id === student.id ? null : student;
    },
    openAddStudentModal() {
      this.addingStudent = true;
      this.currentStudent = {
        name: '',
        age: null,
        gender: '',
        schoolId: null 
      };
    },
    startEdit(student) {
      this.editingStudent = true;
      this.currentStudent = { ...student }; 
    },
    cancelEdit() {
      this.addingStudent = false;
      this.editingStudent = false;
      this.currentStudent = {}; 
    },
    async saveStudent() {
      try {
        if (!this.currentStudent.gender) {
          alert('Por favor, selecione um gênero');
          return;
        }

        let response;
        if (this.addingStudent) {
          response = await axios.post('http://localhost:8080/api/students', this.currentStudent);
        } else if (this.editingStudent) {
          response = await axios.put(
            `http://localhost:8080/api/students/${this.currentStudent.id}`,
            this.currentStudent
          );
        }

        if (response.data) {
          await this.fetchStudents(); // Atualiza a lista de estudantes
          this.cancelEdit(); // Fecha o modal
          alert(this.addingStudent ? 'Estudante adicionado com sucesso!' : 'Estudante atualizado com sucesso!');
        }
      } catch (error) {
        console.error('Erro ao salvar estudante:', error);
        alert('Erro ao salvar estudante');
      }
    },
    confirmDelete(student) {
      if (confirm('Tem certeza que deseja excluir este estudante?')) {
        this.deleteStudent(student.id);
      }
    },
    async deleteStudent(id) {
      try {
        await axios.delete(`http://localhost:8080/api/students/${id}`);
        await this.fetchStudents(); // Atualiza a lista de estudantes após a exclusão
        this.selectedStudent = null; // Limpa a seleção de estudante
        alert('Estudante excluído com sucesso!');
      } catch (error) {
        console.error('Erro ao excluir estudante:', error);
        alert('Erro ao excluir estudante');
      }
    },
    goBack() {
      this.$router.push('/'); 
    }
  }
};
</script>