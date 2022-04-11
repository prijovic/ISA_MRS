<template>
  <div class="carousel-background">
    <div id="slides" class="carousel slide justify-content-center" data-bs-ride="carousel">
      <ul class="carousel-indicators">
        <li v-for="(image, i) in photos" :key="i" :class="i === 0 ? 'active' : ''" data-bs-target="#slides"
            :data-bs-slide-to="i"></li>
      </ul>
      <div class="carousel-inner">
        <div v-for="(image, i) in photos" :key="i" :class="i === 0 ? 'carousel-item active' : 'carousel-item'">
          <img class="d-block w-100" :src="image.photo" alt=""/>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#slides" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#slides" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageSlider",
  props: ["photos"]
}
</script>

<style scoped>

  div.carousel-background {
    /*
    background-color: black;
    border: 1px solid black;
    */
    background-color: lightgray;
  }

  div.carousel {
    height: 60vh !important;
  }

  div.carousel-item {
    transition: -webkit-transform 0.5s ease;
    -webkit-backface-visibility: visible;
    backface-visibility: visible;
  }

  div.carousel-inner img {
    height: 60vh;
    object-fit: contain;
  }

  .carousel-indicators .active{
    background-color: darkblue;
  }

  .carousel-control-prev-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' \
    viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E");
  }

  .carousel-control-next-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' \
    viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E");
  }
</style>