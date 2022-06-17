<template>
  <div class="searchCard tabs mt-5 mx-5" style="display: flex;">
    <div style="display: flex;">
      <font-awesome-icon class="me-2 ms-5 py-1" icon="magnifying-glass" style="color: #008970; font-size: 2rem;"></font-awesome-icon>
      <input class="form-control me-5"  type="text" placeholder="Search" style="border: none;">
    </div>
  </div>
  <div v-if="rentalObjects" class="row mt-5 mx-1 cardMenu">
      <CardView v-for="(rental, i) in rentalObjects" :key="i" :rental="rental"></CardView>
  </div>
  <div v-else class="row mt-5 mx-1 cardMenu" style="border: none; display: flex;">
      <InstructorCardView class="" v-for="(instructor, i) in instructors" :key="i" :instructor="instructor"></InstructorCardView>
  </div>
</template>

<script>
import axios from "axios";
import CardView from "@/components/UnregisteredUser/components/Rental/RentalCardView";
import {useStore} from "vuex";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";
import InstructorCardView from "@/components/UnregisteredUser/components/Instructor/InstructorCardView";
// import InstructorCardView from "@/components/UnregisteredUser/components/InstructorCardView";

library.add(faMagnifyingGlass);

export default {
  name: "CardMenu",
  components: {
    InstructorCardView,
    // InstructorCardView,
    CardView, FontAwesomeIcon
  },
  data() {
    return {
      rentalObjects: null,
      x: [1,2,3,4,5,1,2,3,4,5,1,2,3,4,5],
      instructors: null,
    }
  },
  mounted() {
    const store = useStore();
    let type = this.$route.params.type;
    if(type === "Boat") {
      axios.get("/RentalObjects/getBoats", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(type === "Adventure") {
      axios.get("/RentalObjects/getAdventures", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(type === "VacationRental"){
      axios.get("/RentalObjects/getVacationRentals", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else {
      axios.get("/RentalOwners/getInstructors", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.instructors = response.data.content;
      })
    }
  }
}
</script>

<style scoped>
input[type="text"] {
  font-size: 1.5rem;
  color: darkgray;
  font-weight: 400;
  font-style: italic;
}
input::placeholder {
  font-size: 1.7rem;
  color: #008970;
  font-weight: 500;
  font-style: normal;
}
.searchCard {
  position: relative;
  display: flex;
  flex-direction: column;
  height: fit-content;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: none;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  border-radius: 2rem;
}
div.cardMenu {
  text-align: center;
  justify-content: space-around;
}
</style>