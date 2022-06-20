<template>
  <div class="page-holder ps-1">
    <div class="container-fluid px-lg-4 px-xl-5 p-0 m-0 pt-5 contentDiv">
      <section class="p-0 m-0">
        <div v-if="this.vacationRentalOwner" class="row">
          <div class="col-lg-4" style="height: fit-content; display:block; min-width: 18rem;">
            <div class="card card-profile mb-4" >
              <!--              <div class="card-header"></div>-->
              <div class="card-body text-center">
                <img v-if="this.profilePic" class="card-profile-img" :src="this.profilePic" alt=""/>
                <img v-else class="card-profile-img" src="../../../../Images/vacationRentalNoProfilePic.png" alt=""/>
                <div class="d-flex justify-content-center">
                  <p class="h3">{{ fullName }}</p>
                  <router-link class="btn btn-default ms-2" to="" style="height: fit-content;">
                    <font-awesome-icon class="h5" icon="pencil" style="color: #008970"></font-awesome-icon>
                  </router-link>
                </div>
                <hr>
                <div class="row text-start px-3">
                  <div class="row">
                    <p class="h6">E-mail:&emsp;{{ this.vacationRentalOwner.email }}</p>
                  </div>
                  <div class="row">
                    <p class="h6">Phone:&emsp;{{ this.vacationRentalOwner.phone }}</p>
                  </div>
                  <div class="row mt-3 text-center">
                    <RentalAddress v-if="this.vacationRentalOwner.address" :address="this.vacationRentalOwner.address"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-8">
            <div class="row px-3 mb-2">
              <div class="card tabs pt-2 pb-1">
                <ul class="nav justify-content-center">
                  <li class="nav-item">
                    <p class="h5">
                      <button class="px-3" v-bind:style="{fontWeight:(reviewsBtnClicked)?700:400}"
                              @click="showReviews">Reviews</button>
                    </p>
                  </li>
                  <li class="nav-item">
                    <p class="h5">
                      <button class="px-3" v-bind:style="{fontWeight:(subscribersBtnClicked)?700:400}"
                              @click="showSubscribers">Subscribers</button>
                    </p>
                  </li>
                </ul>
              </div>
            </div>
            <div class="row px-3 pb-5" style="overflow: hidden; overflow-y: scroll; max-height: 85vh;">
              <div  class="m-0 p-0" v-if="subscribersBtnClicked">
                <div class="card px-0 mb-2">
                  <div class="card-header py-2">
                    <h4 class="card-heading">Subscribers</h4>
                  </div>
                  <div class="card-body row text-align: center; justify-content: space-around;">
                    <SubscribedClient v-for="(subscriber, i) in vacationRentalOwner.subscribers" :key="i" :client="subscriber"/>
                  </div>
                </div>
              </div>
              <div class="m-0 p-0" v-if="reviewsBtnClicked">
                <div class="card px-0 mb-2">
                  <div class="card-header py-2">
                    <h4 class="card-heading">Reviews</h4>
                  </div>
                  <div class="card-body">
                    <InstructorProfileReviews :reviews="vacationRentalOwner.clientReviews"
                                              :grade="vacationRentalOwner.grade"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import RentalAddress from "@/components/UnregisteredUser/components/Rental/RentalAddress";
import InstructorProfileReviews from "@/components/UnregisteredUser/components/Instructor/InstructorProfileReviews";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPencil} from "@fortawesome/free-solid-svg-icons";
import SubscribedClient
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorProfile/Subscribers/SubscribedClient";

library.add(faPencil);
export default {
  name: "VacationRentalOwnerProfilPage",
  components: {SubscribedClient, FontAwesomeIcon, RentalAddress, InstructorProfileReviews},
  data() {
    return {
      vacationRentalOwner: null,
      subscribersBtnClicked: false,
      reviewsBtnClicked: true,
      profilePic: null,
    }
  },
  mounted() {
    axios.get("/RentalOwners/getVacationRentalOwner", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      },
      params: {
        id: this.$route.params.id,
      }
    })
        .then((response) => {
          this.vacationRentalOwner = response.data;
          this.getProfilePic();
        })
        .catch((error) => {
          console.log(error);
        });
  },
  computed: {
    fullName() {
      return this.vacationRentalOwner.name + " " + this.vacationRentalOwner.surname;
    },
  },
  methods: {
    getProfilePic() {
      if(!this.vacationRentalOwner.photo) { this.profilePic = null; return; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.vacationRentalOwner.photo,
        },
        responseType: "blob"
      })
          .then(response => {
            this.profilePic = URL.createObjectURL(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
    },
    resetTabButtons() {
      this.subscribersBtnClicked = false;
      this.reviewsBtnClicked = false;
    },
    showSubscribers() {
      this.resetTabButtons();
      this.subscribersBtnClicked = true;
    },
    showReviews() {
      this.resetTabButtons();
      this.reviewsBtnClicked = true;
    },
  },
}
</script>

<style scoped>
.btn-default {
  cursor: pointer;
  user-select: none;
  color: #3f5b25;
  text-decoration: none;
  background-color: white;
  border: none;
}

.btn-default:hover, .btn-default:active, .btn-default:focus {
  border: 1px solid white;
}

div.tabs button {
  border: none;
  background-color: inherit;
}

div.tabs button:hover {

}

::-webkit-scrollbar {
  display: none;
}

button.saveNewDataBtn {
  background-color: black;
  border: 1px solid black;
}

button.saveNewDataBtn:hover {
  background-color: white;
  color: black;
}

.card-header:first-child {
  border-radius: calc(1rem - 1px) calc(1rem - 1px) 0 0;
}

.card-header {
  position: relative;
  padding: 2rem 2rem;
  border-bottom: none;
  background-color: inherit;
  box-shadow: 0 0.125rem 0.25rem rgb(0 0 0 / 8%);
  z-index: 2;
}

.card {
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
  border-radius: 1rem;
}

h1, .h1, h2, .h2, h3, .h3, h4, .h4, h5, .h5, h6, .h6 {
  line-height: 1.2;
}

.page-holder {
  display: flex;
  overflow-x: hidden;
  width: 100%;
  min-height: calc(100vh - 72px);
  flex-wrap: wrap;
}

a {
  color: #4650dd!important;
  text-decoration: underline!important;
  cursor: pointer;
}

.card-profile-img {
  margin-bottom: 1rem;
  border: 3px solid #fff;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  z-index: 2;
  width: 18rem;
  height: 18rem;
  min-width: 12rem;
  min-height: 12rem;
  border-radius: 100%;
  object-fit: cover;
  object-position: center;
}

img, svg {
  vertical-align: middle;
}

.contentDiv {
  padding-top: 4rem;
}

.card-profile .card-header {
  height: 15rem;
  background-position: center center;
  background-size: cover;
}
</style>