<template>
  <div class="page-holder ps-1">
    <div class="container-fluid px-lg-4 px-xl-5 p-0 m-0 pt-5 contentDiv">
      <section class="p-0 m-0">
        <div v-if="this.instructor" class="row">
          <div class="col-5" style="height: fit-content; display:block; min-width: 18rem;">
            <div class="card card-profile mb-4" >
              <div class="card-body text-center">
                <img v-if="this.profilePic" class="card-profile-img" :src="this.profilePic" alt=""/>
                <img v-else class="card-profile-img" src="../../../../../Images/instructorNoProfilePic.png" alt=""/>
                <div class="d-flex justify-content-center">
                  <p class="h3">{{ fullName }}</p>
                  <router-link class="btn btn-default ms-2" :to="'/fishingInstructor/profile/' + this.instructor.id" style="height: fit-content;">
                    <font-awesome-icon class="h5" icon="pencil" style="color: #008970"></font-awesome-icon>
                  </router-link>
                </div>
                <hr>
                <div class="row text-start px-3">
                  <div class="row">
                    <p class="h6">E-mail:&emsp;{{ this.instructor.email }}</p>
                  </div>
                  <div class="row">
                    <p class="h6">Phone:&emsp;{{ this.instructor.phone }}</p>
                  </div>
                  <div class="row mt-3 text-center">
                    <RentalAddress v-if="this.instructor.address" :address="this.instructor.address"/>
                  </div>
                  <div class="row mt-3 text-center">
                    <p class="h3">Unavailability Period</p>
                  </div>
                  <div class="row justify-content-center">
                    <date-picker :key="instructor.id" v-model="range" is-range :first-day-of-week=2 @drag="rangeChanged=true"></date-picker>
                  </div>
                  <div v-if="rangeChanged" class="row mt-1 justify-content-center">
                    <button class="btn btn-red" style="max-width: fit-content" @click="updatePeriod">Save Changes</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-7">
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
                    <SubscribedClient v-for="(subscriber, i) in instructor.subscribers" :key="i" :client="subscriber"/>
                  </div>
                </div>
              </div>
              <div class="m-0 p-0" v-if="reviewsBtnClicked">
                <div class="card px-0 mb-2">
                  <div class="card-header py-2">
                    <h4 class="card-heading">Reviews</h4>
                  </div>
                  <div class="card-body">
                    <InstructorProfileReviews :reviews="instructor.clientReviews"
                                              :grade="instructor.grade"/>
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
import {DatePicker} from "v-calendar";
import SubscribedClient
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorProfile/Subscribers/SubscribedClient";
import {toggleProcessing} from "@/components/state";

library.add(faPencil);

export default {
  name: "InstructorProfile",
  components: {DatePicker, SubscribedClient, FontAwesomeIcon, RentalAddress, InstructorProfileReviews},
  data() {
    return {
      instructor: null,
      subscribersBtnClicked: false,
      reviewsBtnClicked: true,
      profilePic: null,
      range: {
        start: null,
        end: null
      },
      rangeChanged: false
    }
  },
  mounted() {
    axios.get("/RentalOwners/getInstructor", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      },
      params: {
        id: this.$route.params.id,
      }
    })
        .then((response) => {
          this.range.start = response.data.initDate;
          this.range.end = response.data.termDate;
          this.instructor = response.data;
          this.getProfilePic();
        })
        .catch((error) => {
          console.log(error);
        });
  },
  computed: {
    fullName() {
      return this.instructor.name + " " + this.instructor.surname;
    },
  },
  methods: {
    updatePeriod() {
      toggleProcessing();
      const start = this.range.start.toISOString().slice(0, 19);
      const end = this.range.end.toISOString().slice(0, 19);
      console.log(start);
      console.log(end);
      axios.put("/RentalOwners/updateInstructorPeriod", null, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          start: start,
          end: end
        }
      })
      .then(response => {
        this.range.start = response.data.initDate;
        this.range.end = response.data.termDate;
        this.getProfilePic();
        toggleProcessing();
        this.$notify( {
          title: "Successful update",
          text: "You have successfully set unavailability period.",
          position: "bottom right",
          type: "success"
        });
      })
      .catch((error) => {
        toggleProcessing();
        if (!error.response) {
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
        } else if (error.response.status === 500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            position: "bottom right",
            type: "error"
          })
        }
        console.log(error);
      });
    },
    getProfilePic() {
      if(!this.instructor.photo) { this.profilePic = null; return; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.instructor.photo,
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