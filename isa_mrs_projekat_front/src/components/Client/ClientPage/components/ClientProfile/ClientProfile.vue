<template>
  <div class="page-holder ps-1">
    <div class="container-fluid px-lg-4 px-xl-5 p-0 m-0 pt-5 contentDiv">
      <section class="p-0 m-0">
        <div class="row">
          <div class="col-lg-4" style="height: fit-content; display:block; min-width: 18rem;">
            <div class="card card-profile mb-4" >
<!--              <div class="card-header"></div>-->
              <div class="card-body text-center">
                <img v-if="this.profilePic" class="card-profile-img" :src="this.profilePic" alt=""/>
                <img v-else class="card-profile-img" src="https://th.bing.com/th/id/R.4be1aa2ad558d09e7715325f39ee58ec?rik=1PL4Zzb3dyR8Wg&riu=http%3a%2f%2fsimpleicon.com%2fwp-content%2fuploads%2fuser-3.png&ehk=c57lmQWfTHgO6buztac5L2%2bc5XLCNGcgnBoJoB6P4Ms%3d&risl=&pid=ImgRaw&r=0" alt=""/>
                <div class="d-flex justify-content-center">
                  <p class="h3">{{ getClientFullName }}</p>
                  <router-link class="btn btn-default ms-2" to="">
                    <font-awesome-icon class="h5" icon="pencil"></font-awesome-icon>
                  </router-link>
                </div>
                <hr>
                <div class="row text-start px-3">
                  <div class="row">
                    <p class="h6">E-mail:&emsp;{{ this.clientInfo.email }}</p>
                  </div>
                  <div class="row">
                    <p class="h6">Phone:&emsp;{{ this.clientInfo.phone }}</p>
                  </div>
                  <div class="row mt-3 text-center">
                    <RentalAddress v-if="this.clientInfo.address" :address="this.clientInfo.address"/>
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
                      <button class="px-3" v-bind:style="{fontWeight:(upcomingBtnClicked)?700:400}"
                              @click="showUpcomingReservations">Upcoming reservations</button>
                    </p>
                  </li>
                  <li class="nav-item">
                    <p class="h5">
                      <button class="px-3" v-bind:style="{fontWeight:(subscriptionsBtnClicked)?700:400}"
                      @click="showSubscriptions">Subscriptions</button>
                    </p>
                  </li>
                </ul>
              </div>
            </div>
<!----------------------------------------------------Zadovoljna sam--------------------------------------------------->
            <div class="row px-3 pb-5" style="overflow: hidden; overflow-y: scroll; max-height: 85vh;">
              <div  class="m-0 p-0" v-if="subscriptionsBtnClicked">
                <div class="card px-0 mb-2">
                  <div class="card-header py-2">
                    <h4 class="card-heading">Subscriptions</h4>
                  </div>
                  <div class="card-body row text-align: center; justify-content: space-around;">
                    <ClientSubscription v-if="this.clientInfo.subscriptions" :subscriptions="this.clientInfo.subscriptions"/>
                  </div>
                </div>
              </div>
              <div class="m-0 p-0" v-if="upcomingBtnClicked">
                <div class="card px-0 mb-2">
                  <div class="card-header py-2">
                    <h4 class="card-heading">Upcoming</h4>
                  </div>
                  <div class="card-body">
                    <ClientReservations v-if="this.clientInfo.reservations" :reservations="getUpcomingReservations"/>
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
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPencil} from "@fortawesome/free-solid-svg-icons";
import ClientReservations from "@/components/Client/ClientPage/components/ClientProfile/ClientReservations/ClientUpcomingReservations/ClientReservations";
import axios from "axios";
import RentalAddress from "@/components/UnregisteredUser/components/RentalAddress";
import ClientSubscription
  from "@/components/Client/ClientPage/components/ClientProfile/ClientSubscriptions/ClientSubscription";

library.add(faPencil);

export default {
  name: "ClientProfile",
  data() {
    return {
      subscriptionsBtnClicked: false,
      upcomingBtnClicked: true,
      clientInfo: [],
      profilePic: null,
    }
  },
  components: {RentalAddress, ClientReservations, FontAwesomeIcon, ClientSubscription},
  mounted() {
    axios.get("/Users/getClientProfile", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      }
    })
    .then((response) => {
      this.clientInfo = response.data;
      this.getProfilePic();
    })
    .catch((error) => {
      console.log(error);
    });
  },
  computed: {
    getClientFullName() {
      return this.clientInfo.name + " " + this.clientInfo.surname;
    },
    getAddress() {
      return this.address.street + ' ' + this.address.number + ', ' + this.address.city + ', ' + this.address.country;
    },
    getUpcomingReservations() {
      let upcoming = [];
      const today = new Date();
      today.setHours(23, 59, 59, 998);
      for(let i=0; i<this.clientInfo.reservations.length; i++) {
        let date = new Date(this.clientInfo.reservations[i].reservationTime.initDate);
        date.setDate(date.getDate() + 1);
        if(date > today) upcoming.push(this.clientInfo.reservations[i]);
      }
      return upcoming;
    }
  },
  methods: {
    getProfilePic() {
      if(!this.clientInfo.photo) { this.profilePic = null; return; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.clientInfo.photo,
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
      this.subscriptionsBtnClicked = false;
      this.upcomingBtnClicked = false;
    },
    showSubscriptions() {
      this.resetTabButtons();
      this.subscriptionsBtnClicked = true;
    },
    showUpcomingReservations() {
      this.resetTabButtons();
      this.upcomingBtnClicked = true;
    },
  }
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
.form-control:focus {
  border-color: inherit;
  -webkit-box-shadow: none;
  box-shadow: none;
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
.text-muted {
  color: #6c757d !important;
}
.text-sm {
  font-size: .7875rem !important;
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
  /*margin-top: -12rem;*/
  margin-bottom: 1rem;
  border: 3px solid #fff;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  z-index: 2;
  width: 18rem;
  height: 18rem;
  border-radius: 100%;
  object-fit: cover;
  object-position: center;
}
img, svg {
  vertical-align: middle;
}
.avatar.avatar-lg {
  width: 5rem;
  height: 5rem;
  line-height: 5rem;
}
.avatar {
  display: inline-block;
  position: relative;
  width: 3rem;
  height: 3rem;
  text-align: center;
  border: #dee2e6;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  line-height: 3rem;
}
.form-control
{
  color: #343a40;
}
.contentDiv
{
  padding-top: 4rem;
}
.card-profile .card-header {
  height: 15rem;
  background-position: center center;
  background-size: cover;
}
</style>