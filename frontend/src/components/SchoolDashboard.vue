<template>
  <div class="dashboard-container py-5">
    <!-- Cabeçalho -->
    <header class="text-center mb-5">
      <h1 class="dashboard-title">Sistema Escolar</h1>
      <p class="dashboard-subtitle">Gerencie suas escolas e estudantes de forma simples e eficiente</p>
    </header>

    <!-- Lista de Escolas -->
    <section class="list-section">
      <div class="section-header">
        <h2 class="section-title">Lista de Escolas</h2>
        <button class="btn btn-primary" @click="showSchoolForm = true">
          <i class="bi bi-plus-lg"></i> Adicionar Escola
        </button>
      </div>
      <SchoolList ref="schoolList" />
    </section>

    <!-- Lista de Estudantes -->
    <section class="list-section mt-5">
      <div class="section-header">
        <h2 class="section-title">Lista de Estudantes</h2>
        <button class="btn btn-success" @click="showStudentForm = true">
          <i class="bi bi-plus-lg"></i> Adicionar Estudante
        </button>
      </div>
      <StudentList />
    </section>

    <!-- Modal Adicionar Escola -->
    <div v-if="showSchoolForm" class="custom-modal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1>Adicionar Escola</h1>
            <button  class="btn-close" @click="showSchoolForm = false"></button>
          </div>
          <div class="modal-body">
            <AddSchool 
              @school-added="onSchoolAdded" 
              @close="showSchoolForm = false" 
            />
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Adicionar Estudante -->
    <div v-if="showStudentForm" class="custom-modal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title text-success">Adicionar Estudante</h5>
            <button type="button" class="btn-close" @click="showStudentForm = false"></button>
          </div>
          <div class="modal-body">
            <AddStudent 
              @student-added="onStudentAdded" 
              @close="showStudentForm = false" 
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SchoolList from '@/components/SchoolList.vue';
import StudentList from '@/components/StudentList.vue';
import AddStudent from '@/components/AddStudent.vue';
import AddSchool from '@/components/AddSchool.vue';

export default {
  name: 'SchoolDashboard',
  components: {
    SchoolList,
    StudentList,
    AddStudent,
    AddSchool,
  },
  data() {
    return {
      showSchoolForm: false,
      showStudentForm: false,
    };
  },
  methods: {
    refreshSchools() {
      this.$refs.schoolList.fetchSchools();
    },
    onSchoolAdded() {
      this.refreshSchools();
      this.showSchoolForm = false;
    },
    onStudentAdded() {
      this.showStudentForm = false;
    },
  },
};
</script>

<style scoped>
/* Estilo Global */
body {
  background-color: #121212;
  color: #e0e0e0;
  font-family: 'Roboto', sans-serif;
  margin: 0;
  padding: 0;
}

/* Contêiner Geral */
.dashboard-container {
  max-width: 1200px;
  margin: auto;
}

/* Cabeçalho */
.dashboard-title {
  color: #B3EDEC;
  font-size: 3.5rem;
  font-weigh: bold;
  margin-bottom: 10px;
}

.dashboard-subtitle {
  color: #9e9e9e;
  font-size: 1.2rem;
}

/* Seções */
.list-section {
  background-color: #1e1e1e;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  color: #B3EDEC;
  font-size: 1.5rem;
  font-weight: bold;
}

/* Botões */
button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 10px 15px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary {
  background-color: #008C95;
  color: #fff;
}

.btn-primary:hover {
  background-color: #005B68;
}

.btn-success {
  background-color: #2e7d32;
  color: #fff;
}

.btn-success:hover {
  background-color: #1b5e20;
}

.btn-close {
  background: none;
  border: none;
  font-size: 1.2rem;
  color: #e0e0e0;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 999999;
}

/* Modais Personalizados */
.custom-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-dialog {
  max-width: 600px;
  width: 100%;
  border-radius: 8px;
}

.modal-content {
  background-color: #292929;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.6);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-right: 40px;
}

.modal-title {
  font-size: 1.25rem;
}

.modal-body {
  padding: 15px 0;
}

/* Animação */
button:hover {
  transform: scale(1.05);
  transition: transform 0.2s ease-in-out;
}
</style>

