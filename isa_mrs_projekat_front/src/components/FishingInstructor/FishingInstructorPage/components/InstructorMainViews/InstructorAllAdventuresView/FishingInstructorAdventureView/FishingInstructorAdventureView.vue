<template>
  <div class="container-fluid rounded p-2 mb-2">
    <router-link to="/fishingInstructor/adventure" class="link" @click="setRentalId">
      <div class="row main-col align-items-end header rounded">
        <div class="col-sm-5 main-col">
        <div class="col-sm-4 main-col">
          <h4>{{adventure.name}}</h4>
        </div>
        <div class="col-sm-5 main-col">
          <h4>{{adventure.address.street + ", " + adventure.address.city}}</h4>
        </div>
        <div class="col-sm-2 main-col">
          <h4>{{duration}}</h4>
        </div>
        <div class="col-sm-2 main-col">
          <button class="btn" @click.prevent="deleteRental">
            <FontAwesomeIcon icon="trash"></FontAwesomeIcon>
          </button>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faTrash} from "@fortawesome/free-solid-svg-icons";

library.add(faTrash);

export default {
  name: "FishingInstructorAdventureView",
  components: {
    FontAwesomeIcon
  },
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
    },
    deleteRental() {
      alert("DELETE!");
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

  .btn {
    color: white;
    background-color: darkred;
  }
</style>