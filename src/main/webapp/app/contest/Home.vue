<template>
  <div class="custom-body">
    <header>
      <div class="container">
        <nav class="nav">
          <ul class="cab">
            <li class="icon">
              <img src="../../content/images/landing/auto_awesome_black_24dp 1.png" alt="icon" />
            </li>
            <li class="logo">
              <label for="logo">Concurso Gastronômico</label>
            </li>
            <li class="links"><a href="#">Sobre</a></li>
            <li class="links"><a href="#">Mapa gastronômico</a></li>
            <li class="links"><a href="#">Restaurantes</a></li>
            <li class="links"><a href="#dishes">Pratos</a></li>
          </ul>
          <div class="vote-button">
            <div class="vote-text">VOTAR</div>
          </div>
        </nav>
      </div>
    </header>
    <main>
      <section class="home">
        <img class="banner-image" src="../../content/images/landing/banner 1.png" alt="Banner" />
      </section>
      <section class="vaiDeBet-section">
        <div class="vaiDeBet">
          <img src="../../content/images/landing/unsplash_JYGnB9gTCls.png" alt="Vai de Bet" />
          <div class="text">
            <h2>Acesso</h2>
            <h3>Faça parte da nossa história!</h3>
            <h3>Preencha o formulário abaixo para registrar sua avaliação!</h3>
            <input type="button" value="LOGIN" />
          </div>
        </div>
      </section>
      <section class="mais-votados">
        <h2 id="dishes">Pratos</h2>
        <div class="social-midia">
          <div class="card" v-for="dish in paginatedDishes" :key="dish.id">
            <img :src="dish.formattedImage" alt="Dish Image" class="card-img-top" />
            <div class="card-body">
              <h5 class="card-title">{{ dish.title }}</h5>
              <p class="card-text">{{ dish.description }}</p>
            </div>
          </div>
        </div>
        <div class="pagination">
          <button v-for="page in totalPages" :key="page" @click="currentPage = page" :class="{ active: currentPage === page }">
            {{ page }}
          </button>
        </div>
      </section>
      <section class="sobre">
        <div class="sobre-content">
          <h2>Sobre</h2>
          <p>
            A FACISA em parceria com a Prefeitura Municipal de Campina Grande têm o orgulho de apresentar o <br />primeiro Concurso
            Gastronômico do Maior e Melhor São João do Mundo.
          </p>
          <p>
            Estão participando dessa primeira edição xx restaurantes, cada um com um prato específico onde a <br />população escolherá o
            prato mais saboroso, melhor apresentado e o melhor atendimento.
          </p>
          <p>Participe dessa aventura e venha escrever conosco essa história!</p>
        </div>
      </section>
      <section class="roda-pe">
        <div class="se-beber">
          <p class="pe">SE BEBER, NÃO DIRIJA.</p>
        </div>
        <h5>COPYRIGHT © | Todos os direitos reservados. <br />Todas as marcas registradas são propriedades dos seus respectivos donos</h5>
      </section>
    </main>
  </div>
</template>

<script>
import { getDishes } from './logic/home';

export default {
  name: 'LandingPage',
  data() {
    return {
      dishes: [],
      currentPage: 1,
      itemsPerPage: 5,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.dishes.length / this.itemsPerPage);
    },
    paginatedDishes() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.dishes.slice(start, end);
    },
  },
  async mounted() {
    try {
      this.dishes = await getDishes(); // Call the function to fetch dishes
    } catch (error) {
      console.error('Error fetching dishes:', error);
    }
  },
};
</script>

<style scoped src="../../content/css/global.css"></style>
