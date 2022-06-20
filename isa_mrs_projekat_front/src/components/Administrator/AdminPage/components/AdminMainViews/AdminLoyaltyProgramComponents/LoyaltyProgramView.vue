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
            <button type="button" :disabled="primaryValue1===clientPoints&&primaryValue2===ownerPoints" class="btn mt-2" data-bs-toggle="modal" data-bs-target="#confirmationDialog1">Update</button>
            <div class="modal fade" id="confirmationDialog1" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel1">Loyalty Program Update</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    Do you want to change loyalty program?
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                    <button type="button" class="btn" @click.prevent="updateProgram" data-bs-dismiss="modal">Yes</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-8">
          <div class="align-items-center">
            <div class="row main-col">
              <div class="col d-flex justify-content-end pe-0 me-0">
                <router-link to="/admin/loyaltyProgram/newCategory" class="btn btn-default mb-1 d-flex my-auto">
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
                    <button class="btn btn-red" data-bs-toggle="modal" :data-bs-target="'#confirmationDialog'+category.id" ><font-awesome-icon icon="trash"></font-awesome-icon></button>
                    <div class="modal fade" :id="'confirmationDialog'+category.id" tabindex="-1" :aria-labelledby="'exampleModalLabel'+category.id" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" :id="'exampleModalLabel'+category.id">Category Deletion</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            Do you want to delete user category '{{category.name}}'?
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                            <button type="button" class="btn" @click="deleteCategory(category.id)" data-bs-dismiss="modal">Yes</button>
                          </div>
                        </div>
                      </div>
                    </div>
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
import {toggleLoading, toggleProcessing} from "@/components/state";

library.add(faPlus, faTrash);

export default {
  name: "LoyaltyProgramView",
  components: {FontAwesomeIcon},
  data() {
    return {
      primaryValue1: null,
      primaryValue2: null,
      categories: [],
      clientPoints: null,
      ownerPoints: null,
      imageUrls: []
    }
  },
  mounted() {
    toggleLoading();
    const store = useStore();
    axios.get("/LoyaltyProgram/getProgram", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      }
    })
    .then(response => {
      this.categories = response.data.loyaltyCategories;
      this.clientPoints = response.data.clientPointsPerReservation;
      this.ownerPoints = response.data.ownerPointsPerReservation;
      this.primaryValue1 = response.data.clientPointsPerReservation;
      this.primaryValue2 = response.data.ownerPointsPerReservation;
      this.categories.forEach(category => {
        this.loadImage(category.icon, this.categories.indexOf(category));
      });
      toggleLoading();
    })
    .catch(() =>{
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
      toggleLoading();
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
    deleteCategory(categoryId) {
      axios.delete("/LoyaltyProgram/deleteCategory", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          id: categoryId
        }
      })
      .then((response) => {
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
      })
    },
    updateProgram() {
      toggleProcessing();
      const cp = this.clientPoints;
      const op = this.ownerPoints;
      axios.put("/LoyaltyProgram/updateProgram", null, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          clientPointsPerReservation: cp,
          ownerPointsPerReservation: op
        }
      })
      .then(response => {
        this.categories = response.data.loyaltyCategories;
        this.clientPoints = response.data.clientPointsPerReservation;
        this.ownerPoints = response.data.ownerPointsPerReservation;
        this.primaryValue1 = response.data.clientPointsPerReservation;
        this.primaryValue2 = response.data.ownerPointsPerReservation;
        this.categories.forEach(category => {
          this.loadImage(category.icon, this.categories.indexOf(category));
        });
        this.$notify( {
          title: "Successful update",
          text: "You have successfully updated the loyalty program.",
          position: "bottom right",
          type: "success"
        });
        toggleProcessing();
      })
      .catch(() =>{
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
        toggleProcessing()
      });
    },
  }
}
</script>

<style scoped>

</style>