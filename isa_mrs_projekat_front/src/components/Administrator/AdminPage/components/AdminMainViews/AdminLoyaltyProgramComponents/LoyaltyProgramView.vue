<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="row container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="col-4 text-center">
          <div class="rounded shadow p-3">
            <h3 style="color: #008970" class="mb-3">Loyalty Program</h3>
            <label for="client" style="color: #008970">Client Points Per Reservation</label>
            <input v-model="clientPoints" type="number" :min=0 id="client" class="form-control mb-2">
            <label for="owner" style="color: #008970">Owner Points Per Reservation</label>
            <input v-model="ownerPoints" type="number" :min=0 id="owner" class="form-control">
          </div>
        </div>
        <div class="col-8">
          <div class="align-items-center">
            <div class="row main-col">
              <div class="col d-flex justify-content-end pe-0 me-0">
                <router-link to="/admin/newUser" class="btn btn-default mb-1 d-flex my-auto">
                  <font-awesome-icon class="my-auto pe-2" icon="plus"></font-awesome-icon>
                  Add Category
                </router-link>
              </div>
            </div>
            <div class="row main-col text-center header rounded mb-3">
              <table>
                <thead>
                <tr>
                  <th>Icon</th>
                  <th>Name</th>
                  <th>Points</th>
                  <th>Client Discount</th>
                  <th>Owner Bonus</th>
                  <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(category, index) in this.categories" :key="index" :class="index%2!==0?'odd':'even'">
                  <td>
                    <img :src="imageUrls[index]" style="height: 50px;width: 50px; object-fit: cover; object-position: center;" class="img-fluid rounded p-0" alt="">
                  </td>
                  <td>{{category.name}}</td>
                  <td>{{category.requiredPoints}}</td>
                  <td>{{category.clientDiscount.toFixed(2)}}%</td>
                  <td>{{category.ownerBenefit.toFixed(2)}}%</td>
                  <td>
                    <button class="btn btn-red"><font-awesome-icon icon="trash"></font-awesome-icon></button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faTrash } from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";
import axios from "axios";

library.add(faPlus, faTrash);

export default {
  name: "LoyaltyProgramView",
  components: {FontAwesomeIcon},
  data() {
    return {
      categories: [],
      clientPoints: null,
      ownerPoints: null,
      imageUrls: []
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/LoyaltyProgram/getProgram", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      }
    })
    .then(response => {
      this.categories = response.data.loyaltyCategories;
      this.clientPoints = response.data.clientPointsPerReservation;
      this.ownerPoints = response.data.ownerPointsPerReservation;
      this.categories.forEach(category => {
        this.loadImage(category.icon, this.categories.indexOf(category));
      });
    })
    .catch(() =>{
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
    });
  },
  methods: {
    loadImage(name, index) {
      axios.get("/Photos/", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: name,
        },
        responseType: "blob"
      })
      .then(response => {
        this.imageUrls[index] = URL.createObjectURL(response.data);
      })
      .catch((error) =>{
        console.log(error);
      });
    },
  }
}
</script>

<style scoped>

</style>