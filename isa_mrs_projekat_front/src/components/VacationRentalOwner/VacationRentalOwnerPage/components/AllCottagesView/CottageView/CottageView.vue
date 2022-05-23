<template>
  <div class="container-fluid rounded shadow p-2 mb-2">
    <router-link to="/#" class="link">
      <div class="row main-col align-items-end header rounded">
        <div class="col-sm-4 main-col my-auto">
          <h4>{{vacationRentalHouse.name}}</h4>
        </div>
        <div class="col-sm-4 main-col my-auto">
          <h4>{{vacationRentalHouse.address.street + ", " + vacationRentalHouse.address.city}}</h4>
        </div>
        <div class="col-sm-2 main-col my-auto">
          <h4>{{vacationRentalHouse.price}}</h4>
        </div>
        <div class="col-sm-2 main-col my-auto text-center">
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
  name: "CottageView",
  components: {
    FontAwesomeIcon
  },
  props: ["vacationRentalHouse"],
  methods:{
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