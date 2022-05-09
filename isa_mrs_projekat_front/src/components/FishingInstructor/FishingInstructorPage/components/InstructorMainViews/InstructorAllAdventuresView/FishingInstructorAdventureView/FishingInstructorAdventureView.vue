<template>
  <div class="container-fluid rounded p-2 mb-2">
    <router-link to="/fishingInstructor/adventure" class="link" @click="setRentalId">
      <div class="row main-col align-items-end header rounded">
        <div class="col-sm-5 main-col">
          <h4>{{adventure.name}}</h4>
        </div>
        <div class="col-sm-5 main-col">
          <h4>{{adventure.address.street + ", " + adventure.address.city}}</h4>
        </div>
        <div class="col-sm-2 main-col">
          <h4>{{duration}}</h4>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "FishingInstructorAdventureView",
  props: ["adventure"],
  computed: {
    duration() {
      const duration = this.adventure.duration;
      if (duration < 1) {
        return Math.round(60*duration) + "min";
      } else {
        let minutes = duration%1 === 0 ? "" : 60*(duration%1) + "min";
        return Math.trunc(duration) + "h " + minutes;
      }
    }
  },
  methods: {
    setRentalId() {
      this.$store.dispatch("rentalId", this.adventure.id);
    }
  }
}
</script>

<style scoped>
  .container-fluid {
    background-color: #f7f7f2;
  }

  .link {
    text-decoration: none;
    color: #378220;
  }
</style>