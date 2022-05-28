<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col">
            <div class="col d-flex justify-content-start ps-0 ms-0 me-0 pe-0">
              <div class="col mb-1">
                <div class="d-flex flex-row justify-content-end">
                  <button v-if="hasChanged" class="btn btn-red mb-1 me-1" data-bs-toggle="modal" data-bs-target="#confirmationDialog">
                    Save Changes
                  </button>
                </div>
                <div class="modal fade" id="confirmationDialog" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Rental Objects' Status Change</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        {{modalMessage}}
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                        <button type="button" class="btn" @click="saveChanges" data-bs-dismiss="modal">Yes</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row main-col text-center header rounded mb-3">
            <table>
              <thead>
              <tr>
                <th></th>
                <th>Name</th>
                <th>Destination</th>
                <th>Duration</th>
                <th></th>
                <th>Price</th>
                <th>Active</th>
              </tr>
              </thead>
              <tbody>
              <tr class="p-1" v-for="(rentalObject, index) in this.adventures" :key="index" :class="index%2!==0?'odd':'even'">
                <td class="col-1">
                  <img v-if="rentalObject.photos.length !== 0" :src="imageUrls[index]" style="height: 6vh;width: 6vh" class="img-fluid rounded border-1" alt="">
                  <font-awesome-icon v-else icon="user" class="img-fluid rounded border-1" style="height: 3vh"></font-awesome-icon>
                </td>
                <td>
                  <router-link class="profile-link" :to="'/fishingInstructor/adventure/' + rentalObject.id">{{rentalObject.name}}</router-link>
                </td>
                <td>
                  {{rentalObject.address.city + ", " + rentalObject.address.country}}
                </td>
                <td>
                  {{duration(rentalObject.duration)}}
                </td>
                <td>
                  <button :id="index" class="eye-btn border-0 text-decoration-none" type="button" data-bs-toggle="modal" :data-bs-target="'#profileModal' + index">
                    <font-awesome-icon class="text-decoration-underline" icon="eye"></font-awesome-icon>
                  </button>
                  <div class="modal fade" :id="'profileModal' + index" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Adventure Profile</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <div class="row justify-content-center">
                            <div class="row">
                              <div class="col me-lg-3 me-md-2">
                                <div class="row text-center">
                                  <img v-if="rentalObject.photos.length !== 0" :src="imageUrls[index]" class="img-fluid rounded p-0" alt="">
                                  <font-awesome-icon v-else icon="user" class="img-fluid rounded p-0" style="background-color: #B0B8B4FF; color: white"></font-awesome-icon>
                                </div>
                              </div>
                              <div class="col">
                                <div class="row text-center">
                                  <div class="container-fluid rounded" style="border: 1px solid #008970; color: #008970" >
                                    <h5>{{rentalObject.name}}</h5>
                                    <hr>
                                    <div class="row pb-5">
                                      <div class="col text-start">
                                        <h6>Price: <span style="color: black">{{rentalObject.price}}$</span></h6>
                                        <h6>Description: <span style="color: black">{{rentalObject.description}}</span></h6>
                                        <h6>Address: <span style="color: black">{{rentalObject.address.street + " " + rentalObject.address.number + ", " + rentalObject.address.city + ", " + rentalObject.address.country}}</span></h6>
                                        <h6>Activity:
                                          <input v-if="rentalObject.isActive" class="form-check-input" type="checkbox" checked disabled>
                                          <input v-else class="form-check-input" type="checkbox" disabled></h6>
                                        <h6>Capacity: <span style="color: black">{{rentalObject.capacity}}</span></h6>
                                        <h6>Equipment: <span style="color: black">{{equipment(rentalObject.adventureEquipment)}}</span></h6>
                                        <h6>Rules of Conduct:</h6>
                                        <table class="rounded">
                                          <thead style="background-color: transparent">
                                            <tr>
                                              <th style="background-color: #008970; color: #f7f7f2; ; border-bottom: #008970 1px solid">Do</th>
                                              <th style="background-color: #e23c52; color: #f7f7f2; border-left: #008970 1px solid; border-bottom: #e23c52 1px solid">Don't</th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                            <tr v-for="(rule, index) in rulesOfConduct(rentalObject.conductRules)" :key="index" :class="index%2!==0?'odd':'even'">
                                              <td style="border-right: #008970 1px solid">{{rule.Do}}</td>
                                              <td>{{rule.DoNot}}</td>
                                            </tr>
                                          </tbody>
                                        </table>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
                <td>
                  {{rentalObject.price + "$"}}
                </td>
                <td>
                  <input class="form-check-input" type="checkbox" :value="!rentalObject.isActive" v-model="rentalObject.isActive" @change="changeStatus(rentalObject)" :disabled="!rentalObject.isDeletable">
                </td>
              </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-center mt-3">
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage===0" @click="previousPage">Previous</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="currentPage > 1 && totalPages > 3">...</li>
                <li class="page-item"><button class="page-link" :disabled="currentPage === 0" @click="numberedPage(1)">{{button1Content}}</button></li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage !== 0 && totalPages - currentPage > 1" @click="numberedPage(2)">{{button2Content}}</button></li>
                <li class="page-item" v-if="totalPages > 2"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="numberedPage(3)">{{button3Content}}</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="totalPages - currentPage > 1 && totalPages > 3 && button3Content !== totalPages">...</li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="nextPage">Next</button></li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faAngleDoubleLeft, faAngleDoubleRight, faAngleLeft, faAngleRight, faFrown}
  from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";

library.add(faAngleRight, faAngleLeft, faFrown, faAngleDoubleRight, faAngleDoubleLeft);

export default {
  name: "AdventuresView",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      adventures: [],
      totalPages: null,
      currentPage: 0,
      pageSize: 10,
      imageUrls: [],
      changedRentals: []
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/RentalObjects/getAdventuresInstructor", {headers: {
        Authorization: "Bearer " + store.state.access_token,
      },
      params: {
        page: this.currentPage,
        pageSize: this.pageSize,
        field: "name"
      }},
    ).then(response => {
      this.adventures = response.data.content;
      this.currentPage = response.data.currentPage;
      this.totalPages = response.data.pages;
      this.adventures.forEach(rentalObject => {
        if (rentalObject.photos.length > 0) {
          const index = this.adventures.indexOf(rentalObject);
          this.loadImage(rentalObject.photos[0].photo, index);
        }
      })
    });
  },
  methods: {
    saveChanges() {
      let ids = this.changedRentalsIds;
      let lwc = {list: ids};
      axios.put("/RentalObjects/multipleRentalsStatusChange",
          lwc,
          {headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            }
          })
          .then((response) => {
            let message = "You have successfully changed status for rental objects:";
            response.data.forEach((rentalObject) => {message += " " + rentalObject.name + ","});
            message = message.slice(0, -1);
            message += ".";
            this.$notify({
              title: "Status Change Successful",
              text: message,
              type: "success"
            });
            this.changedRentals = [];
          })
          .catch(() =>{
            this.$notify({
              title: "Server error",
              text: "Something went wrong. Please try again later...",
              type: "error"
            });
          })
    },
    changeStatus(rentalObject) {
      let index = this.changedRentals.findIndex(element => element.id === rentalObject.id);
      if (index > -1) {
        this.changedRentals.splice(index, 1);
      } else {
        this.changedRentals.push(rentalObject);
      }
    },
    previousPage() {
      this.currentPage -= 1;
      this.refreshPage();
    },
    nextPage() {
      this.currentPage += 1;
      this.refreshPage();
    },
    numberedPage(buttonNumber) {
      if (buttonNumber === 1) {
        this.currentPage = this.button1Content - 1;
      } else if (buttonNumber === 2) {
        this.currentPage = this.button2Content - 1;
      } else if (buttonNumber === 3) {
        this.currentPage = this.button3Content - 1;
      }
      this.refreshPage();
    },
    refreshPage() {
      axios.get("/RentalObjects/getAdventureInstructor", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          page: this.currentPage,
          pageSize: this.pageSize,
          field: "name"
        }
      })
      .then(response => {
        this.adventures = response.data.content;
        this.currentPage = response.data.currentPage;
        this.totalPages = response.data.pages;
        this.adventures.forEach(rentalObject => {
          if (rentalObject.photos.length > 0) {
            const index = this.adventures.indexOf(rentalObject);
            this.loadImage(rentalObject.photos[0].photo, index);
          }
        })
      })
      .catch(() =>{
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
      });
    },
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
    duration(duration) {
      if (duration < 1) {
        return Math.round(60*duration) + "min";
      } else {
        let minutes = duration%1 === 0 ? "" : 60*(duration%1) + "min";
        return Math.trunc(duration) + "h " + minutes;
      }
    },
    equipment(equipment) {
      let result = "";
      equipment.forEach(element => {
        result += " " + element.name + ",";
      })
      result = result.slice(0, -1);
      return result;
    },
    rulesOfConduct(rulesOfConduct) {
      let pairsOfRules = [];
      let positiveRules = this.positiveRules(rulesOfConduct);
      let negativeRules = this.negativeRules(rulesOfConduct);
      let size = Math.max(positiveRules.length, negativeRules.length);
      for (let i = 0; i < size; i++) {
        pairsOfRules.push({
          "Do": i+1<=positiveRules.length?positiveRules[i]:"",
          "DoNot": i+1<=negativeRules.length?negativeRules[i]:""
        });
      }
      return pairsOfRules;
    },
    positiveRules(rulesOfConduct) {
      let positiveRules = [];
      rulesOfConduct.forEach(rule => {
        if (rule.type === "Do") {
          positiveRules.push(rule.rule);
        }
      });
      return positiveRules;
    },
    negativeRules(rulesOfConduct) {
      let negativeRules = [];
      rulesOfConduct.forEach(rule => {
        if (rule.type === "DoNot") {
          negativeRules.push(rule.rule);
        }
      });
      return negativeRules;
    }
  },
  computed: {
    changedRentalsIds() {
      let ids = [];
      this.changedRentals.forEach(rentalObject => {ids.push(rentalObject.id)});
      return ids;
    },
    hasChanged() {
      return this.changedRentals.length > 0;
    },
    button1Content() {
      return this.currentPage === 0 ? this.currentPage+1: this.totalPages - this.currentPage === 1 ? this.currentPage - 1 : this.currentPage;
    },
    button2Content() {
      return this.currentPage === 0 ? this.currentPage+2: this.totalPages - this.currentPage === 1 ? this.currentPage : this.currentPage+1;
    },
    button3Content() {
      return this.currentPage === 0 ? this.currentPage+3: this.totalPages - this.currentPage === 1 ? this.currentPage + 1 : this.currentPage+2;
    },
    modalMessage() {
      let numberOfDeletedRentals = this.numberOfDeletedRentals;
      let numberOfActivatedRentals = this.numberOfActivatedRentals;
      let deleteMessage = "";
      let activateMessage = "";
      if (numberOfDeletedRentals > 1) {
        deleteMessage = "Do you want to delete adventures:";
      } else if (numberOfDeletedRentals === 1) {
        deleteMessage = "Do you want to delete adventure:";
      }
      if (numberOfActivatedRentals > 1) {
        activateMessage = "Do you want to activate adventures:";
      } else if (numberOfActivatedRentals === 1) {
        activateMessage = "Do you want to activate adventure:";
      }
      this.changedRentals.forEach(rentalObject => {
        if (!rentalObject.isActive) {
          deleteMessage += " " + rentalObject.name + ",\n";
        } else {
          activateMessage += " " + rentalObject.name + ",\n";
        }
      });
      if (numberOfDeletedRentals > 0) {
        deleteMessage = deleteMessage.slice(0, -2);
        deleteMessage += "?";
      }
      if (numberOfActivatedRentals > 0) {
        activateMessage = activateMessage.slice(0, -2);
        activateMessage += "?";
      }
      return deleteMessage + "\n" + activateMessage;
    },
    numberOfDeletedRentals() {
      let counter = 0;
      this.changedRentals.forEach(rentalObject => {
        if (!rentalObject.isActive) {
          counter += 1;
        }
      });
      return counter;
    },
    numberOfActivatedRentals() {
      let counter = 0;
      this.changedRentals.forEach(rentalObject => {
        if (rentalObject.isActive) {
          counter += 1;
        }
      });
      return counter;
    }
  }
}
</script>

<style scoped>
  .profile-link {
    color: #008970;
  }
</style>