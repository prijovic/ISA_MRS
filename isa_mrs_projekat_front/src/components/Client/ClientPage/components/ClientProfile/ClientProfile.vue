<template>
  <div class="page-holder ps-1">
    <div class="container-fluid px-lg-4 px-xl-5 p-0 m-0 pt-5 contentDiv">
      <section class="p-0 m-0">
        <div class="row">
          <div class="col-lg-4" style="height: fit-content; display:block">
            <div class="card card-profile mb-4" >
<!--              <div class="card-header"></div>-->
              <div class="card-body text-center"><img class="card-profile-img"
                  src="https://th.bing.com/th/id/R.aafb49c4c103a2cedaa573cfa520f396?rik=h%2frtPzBFQrLrEw&pid=ImgRaw&r=0" alt="Jassa Rich">
                <div class="d-flex justify-content-center">
                  <p class="h3">Tamara Volas</p>
                  <router-link class="btn btn-default ms-2" to="">
                    <font-awesome-icon class="h5" icon="pencil"></font-awesome-icon>
                  </router-link>
                </div>
                <hr>
                <div class="row text-start px-3">
                  <p class="h6">E-mail:&emsp;volastamara@gmail.com</p>
                  <p class="h6">Address:</p>
                </div>
              </div>
            </div>
<!--            <form class="card mb-4">-->
<!--              <div class="card-header py-3">-->
<!--                <h4 class="card-heading">Password</h4>-->
<!--              </div>-->
<!--              <div class="card-body">-->
<!--                <div class="row mb-4">-->
<!--                  <label class="form-label mb-0">Old password</label>-->
<!--                  <input class="form-control mt-0" type="password" value="">-->
<!--                </div>-->
<!--                <div class="row mb-4">-->
<!--                  <label class="form-label mb-0">New password</label>-->
<!--                  <input class="form-control mt-0" type="password" value="">-->
<!--                </div>-->
<!--                <div class="row">-->
<!--                  <label class="form-label mb-0">Confirm new password</label>-->
<!--                  <input class="form-control mt-0" type="password" value="">-->
<!--                </div>-->
<!--              </div>-->
<!--              <div class="card-footer text-end">-->
<!--                <button class="btn btn-primary saveNewDataBtn">Save</button>-->
<!--              </div>-->
<!--            </form>-->
          </div>
          <div class="col-lg-8">
            <div class="row px-3 mb-2">
              <div class="card tabs pt-2 pb-1">
                <ul class="nav justify-content-center">
<!--                  <li class="nav-item">-->
<!--                    <p class="h5">-->
<!--                      <button class="px-3" v-bind:style="{fontWeight:(vacationRentalsBtnClicked)?700:400}"-->
<!--                              @click="showVacationRentalsReservations()">Vacation rentals</button>-->
<!--                    </p>-->
<!--                  </li>-->
<!--                  <li class="nav-item">-->
<!--                    <p class="h5">-->
<!--                      <button class="px-3" v-bind:style="{fontWeight:(boatsBtnClicked)?700:400}"-->
<!--                              @click="showBoatsReservations">Boats</button>-->
<!--                    </p>-->
<!--                  </li>-->
<!--                  <li class="nav-item">-->
<!--                    <p class="h5">-->
<!--                      <button class="px-3" v-bind:style="{fontWeight:(adventuresBtnClicked)?700:400}"-->
<!--                              @click="showAdventuresReservations">Adventures</button>-->
<!--                    </p>-->
<!--                  </li>-->
                  <li class="nav-item">
                    <p class="h5">
                      <button class="px-3" v-bind:style="{fontWeight:(subscriptionsBtnClicked)?700:400}"
                      @click="showSubscriptions">Subscriptions</button>
                    </p>
                  </li>
                  <li class="nav-item">
                    <p class="h5">
                      <button class="px-3" v-bind:style="{fontWeight:(upcomingBtnClicked)?700:400}"
                              @click="showUpcomingReservations">Upcoming reservations</button>
                    </p>
                  </li>
                </ul>
              </div>
            </div>
<!----------------------------------------------------Zadovoljna sam--------------------------------------------------->
            <div class="row px-3" style="overflow: hidden; overflow-y: scroll; max-height: 85vh;">
              <div class="m-0 p-0" v-if="rentalClicked">
                <ClientReservations></ClientReservations>
              </div>
              <div v-if="subscriptionsBtnClicked">Sub</div>
              <div v-if="upcomingBtnClicked">Upcoming</div>
<!--              <div class="card px-0 mb-2">-->
<!--                <div class="card-header py-2">-->
<!--                  <h4 class="card-heading">Upcoming</h4>-->
<!--                </div>-->
<!--                <div class="card-body">-->
<!--                  Rezervacija ili NONE-->
<!--                </div>-->
<!--              </div>-->
<!--              <div class="card px-0 mb-5">-->
<!--                <div class="card-header py-2">-->
<!--                  <h4 class="card-heading">Reservation history</h4>-->
<!--                </div>-->
<!--                <div class="card-body">-->
<!--                  Rezervacija ili NONE-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->






<!--              <div class="card overflow-hidden mb-4">-->
<!--                <div class="card-header">-->
<!--                  <div class="input-group">-->
<!--                    <input class="form-control" type="text" placeholder="Message">-->
<!--                    <button class="btn btn-outline-secondary" type="button"><i class="fa fa-paper-plane"></i></button>-->
<!--                  </div>-->
<!--                </div>-->
<!--                <div class="list-group rounded-0">-->
<!--                  <div class="list-group-item border-start-0 border-end-0 py-5 border-top-0">-->
<!--                    <div class="d-flex">-->
<!--                      <div class="flex-shrink-0"><img class="avatar avatar-lg p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt=""></div>-->
<!--                      <div class="flex-grow-1 ps-3"><small class="float-right">10 mins ago</small>-->
<!--                        <h5 class="fw-bold">Jassa Rich</h5>-->
<!--                        <div class="text-muted text-sm"> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                        <div class="d-flex mt-4">-->
<!--                          <div class="flex-shrink-0"><img class="avatar avatar-md p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt="Serenity Mitchelle"></div>-->
<!--                          <div class="flex-grow-1 ps-3 text-sm text-muted"><strong class="text-dark">Serenity Mitchelle: </strong>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                        </div>-->
<!--                        <div class="d-flex mt-4">-->
<!--                          <div class="flex-shrink-0"><img class="avatar avatar-md p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt="Tony O'Brian"></div>-->
<!--                          <div class="flex-grow-1 ps-3 text-sm text-muted"><strong class="text-dark">Tony O'Brian: </strong>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                        </div>-->
<!--                      </div>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                  <div class="list-group-item border-start-0 border-end-0 py-5">-->
<!--                    <div class="d-flex">-->
<!--                      <div class="flex-shrink-0"><img class="avatar avatar-lg p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt="Jassa Rich"></div>-->
<!--                      <div class="flex-grow-1 ps-3"><small class="float-right">12 mins ago</small>-->
<!--                        <h5 class="fw-bold">Jassa Rich</h5>-->
<!--                        <div class="text-muted text-sm"> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                      </div>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                  <div class="list-group-item border-start-0 border-end-0 py-5">-->
<!--                    <div class="d-flex">-->
<!--                      <div class="flex-shrink-0"><img class="avatar avatar-lg p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt="Jassa Rich"></div>-->
<!--                      <div class="flex-grow-1 ps-3"><small class="float-right">34 mins ago</small>-->
<!--                        <h5 class="fw-bold">Jassa Rich</h5>-->
<!--                        <div class="text-muted text-sm"> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                        <div class="d-flex mt-4">-->
<!--                          <div class="flex-shrink-0"><img class="avatar avatar-md p-1" src="https://therichpost.com/wp-content/uploads/2021/03/avatar2.png" alt="Javier Gregory"></div>-->
<!--                          <div class="flex-grow-1 ps-3 text-sm text-muted"><strong class="text-dark">Javier Gregory: </strong>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</div>-->
<!--                        </div>-->
<!--                      </div>-->
<!--                    </div>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->





            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import ClientReservations from "@/components/Client/ClientPage/components/ClientProfile/ClientReservations/ClientReservations";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPencil} from "@fortawesome/free-solid-svg-icons";

library.add(faPencil);

export default {
  name: "ClientProfile",
  data() {
    return {
      rentalClicked: false,
      vacationRentalsBtnClicked: false,
      boatsBtnClicked: false,
      adventuresBtnClicked: false,
      subscriptionsBtnClicked: true,
      upcomingBtnClicked: false
    }
  },
  components: {ClientReservations, FontAwesomeIcon},
  mounted() {
    // const store = useStore();
    // this.clientId = store.state.

  },
  methods: {
    resetTabButtons() {
      this.rentalClicked = false;
      this.vacationRentalsBtnClicked = false;
      this.boatsBtnClicked = false;
      this.adventuresBtnClicked = false;
      this.subscriptionsBtnClicked = false;
      this.upcomingBtnClicked = false;
    },
    showVacationRentalsReservations() {
      this.resetTabButtons();
      this.rentalClicked = true;
      this.vacationRentalsBtnClicked = true;
    },
    showBoatsReservations() {
      this.resetTabButtons();
      this.rentalClicked = true;
      this.boatsBtnClicked = true;
    },
    showAdventuresReservations() {
      this.resetTabButtons();
      this.rentalClicked = true;
      this.adventuresBtnClicked = true;
    },
    showSubscriptions() {
      this.resetTabButtons();
      this.subscriptionsBtnClicked = true;
    },
    showUpcomingReservations() {
      this.resetTabButtons();
      this.upcomingBtnClicked = true;
    }
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