<template>
  <div class="dashboard-container py-5">
  <a @click="goBack" href="#" class="btn-link-home">Página Inicial</a>
    <header class="text-center mb-5">
      <h1 class="dashboard-title">Gerenciar Escolas</h1>
    </header>

    <div class="header">
      <h2 class="section-title">Lista de Escolas</h2>
      <div class="controls">
        <button @click="openAddSchoolModal" class="btn btn-primary">Adicionar Escola</button>
      </div>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th>Nome da Escola</th>
          <th>Localização</th>
          <th>Diretor</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="school in schools"
          :key="school.id"
          @click="selectSchool(school)"
          :class="{ 'selected': selectedSchool?.id === school.id }"
        >
          <td>{{ school.schoolName }}</td>
          <td>{{ school.location }}</td>
          <td>{{ school.principalName }}</td>
          <td>
            <div class="button-group">
              <!-- Aqui, chamamos a função startEdit passando a escola para edição -->
              <button @click.stop="startEdit(school)" class="btn btn-secondary-action">Editar</button>
              <button @click.stop="confirmDelete(school)" class="btn btn-danger-action">Excluir</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal de Edição / Adição -->
    <div v-if="editingSchool || addingSchool" class="modal">
      <div class="modal-content">
        <h2>{{ addingSchool ? 'Adicionar Escola' : 'Editar Escola' }}</h2>
        <form @submit.prevent="saveSchool">
          <label>Nome da Escola:
            <input v-model="currentSchool.schoolName" required />
          </label>
          <label>Localização:
            <input v-model="currentSchool.location" required />
          </label>
          <label>Nome do Diretor:
            <input v-model="currentSchool.principalName" required />
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
      schools: [],
      selectedSchool: null,
      editingSchool: null,
      addingSchool: false,  // Para controlar a visibilidade do modal de adicionar
      currentSchool: {      // Para os dados da escola sendo editada ou adicionada
        schoolName: '',
        location: '',
        principalName: '',
      }
    };
  },

  created() {
    this.fetchSchools();
  },

  methods: {
    openAddSchoolModal() {
      this.addingSchool = true;  // Exibe o modal de adição
      this.currentSchool = {     // Reseta os campos para adição
        schoolName: '',
        location: '',
        principalName: '',
      };
    },

   
    cancelEdit() {
      this.addingSchool = false;
      this.editingSchool = null;
      this.currentSchool = {      // Limpa os campos
        schoolName: '',
        location: '',
        principalName: '',
      };
    },

    startEdit(school) {
      // Preenche os campos com os dados da escola a ser editada
      this.editingSchool = { ...school };
      this.currentSchool = { ...school };  // Atualiza currentSchool com os dados da escola
      this.addingSchool = false;  // Garante que estamos no modo de edição
    },

    async saveSchool() {
      try {
        if (this.addingSchool) {
          const response = await axios.post('http://localhost:8080/api/schools', this.currentSchool);
          console.log(response);  

          if (response.status === 200 || response.status === 201) {
            console.log('Escola adicionada:', response.data);  
            alert('Escola adicionada com sucesso!');
            await this.fetchSchools();  
            this.cancelEdit();  
          } else {
            console.error('Erro ao adicionar escola.Status:', response.status);  
            await this.fetchSchools();  
            alert('Erro ao adicionar escola.');
          }
        } else if (this.editingSchool) {
          // Edita uma escola existente
          const response = await axios.put(
            `http://localhost:8080/api/schools/${this.currentSchool.id}`,
            this.currentSchool
          );
          console.log(response);  // Log para ver a resposta da edição

          if (response.status === 200) {
            await this.fetchSchools();  // Recarrega a lista de escolas
            alert('Escola atualizada com sucesso!');
            this.cancelEdit();  // Fecha o modal
          } else {
            console.error('Erro ao atualizar escola:', response);  // Log de erro na edição
            alert('Erro ao atualizar escola.');
          }
        }
      } catch (error) {
        console.error('Erro ao salvar escola:', error);
        alert('Erro ao salvar escola.');
      }
    },


    async fetchSchools() {
      try {
        const response = await axios.get('http://localhost:8080/api/schools');
        this.schools = response.data;
      } catch (error) {
        console.error('Erro ao buscar escolas:', error);
      }
    },

    selectSchool(school) {
      this.selectedSchool = this.selectedSchool?.id === school.id ? null : school;
    },

    confirmDelete(school) {
      if (confirm('Tem certeza que deseja excluir esta escola?')) {
        this.deleteSchool(school.id);
      }
    },

    async deleteSchool(id) {
      try {
        await axios.delete(`http://localhost:8080/api/schools/${id}`);
        await this.fetchSchools();
        this.selectedSchool = null;
        alert('Escola excluída com sucesso!');
      } catch (error) {
        console.error('Erro ao excluir escola:', error);
        alert('Erro ao excluir escola');
      }
    },
    goBack() {
      this.$router.push('/'); 
    }
  }
};
</script>
