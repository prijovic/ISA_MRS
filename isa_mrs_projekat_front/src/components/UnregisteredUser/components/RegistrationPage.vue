<template>
  <div class="container-fluid border">
    <div class="row main">
      <div class="col main pt-3">
        <div class="row">
          <div class="col-4 main"></div>
          <div class="col-6 main">
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Type of user:</i></p>
              <select class="form-control">
                <option value="client" selected="selected">Client</option>
                <option value="vacationRentalOwner">Vacation Rental Owner</option>
                <option value="boatOwner">Boat Owner</option>
                <option value="instructor">Instructor</option>
              </select>
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Name:</i></p>
              <input class="ps-1 h5" type="text" id="name" placeholder="E.g. Tamara">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Surname:</i></p>
              <input class="ps-1 h5" type="text" id="surname" placeholder="E.g. Volas">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>E-mail:</i></p>
              <input class="ps-1 h5" type="email" id="email" placeholder="E.g. john@gmail.com">
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Phone:</i></p>
              <MazPhoneNumberInput v-model="phoneNumber" @update="results = $event"/>
            </div>
            <div class="row">
              <p class="h6 ps-0 pb-0"><i>Profile pic <small>(optional)</small>:</i></p>
              <input class="p-0 h5" type="file" name="profilePic" accept="image/jpeg"/>
              <small class="p-0" style="color: grey;">Acceptable: jpg</small>
            </div>
          </div>
          <div class="col-2 main"></div>
        </div>
      </div>
      <div class="col main pt-3">
        <div class="row main">
          <div class="col-2 main"></div>
          <div class="col-6 main">
            <div class="row">
              <p class="h1 p-0"><i class="fa-solid fa-house"></i></p>
            </div>
            <div class="row mb-3">
              <p class="h6 ps-0 pb-0"><i>Query:</i></p>
              <input v-model="query" class="ps-1 h5" type="text" id="query">
            </div>
            <AddressLookup ref="validator" :query="query"></AddressLookup>
            <div class="row">
              <button @click="validate()" type="button" class="btn btn-secondary">Validate</button>
            </div>
          </div>
          <div class="col-4 main">
          </div>
        </div>
      </div>
    </div>
    <div class="row main pt-3">
      <div class="col-4 main"></div>
      <div class="col-4 main px-5">
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Password:</i></p>
          <input class="ps-1 h5" type="password" id="password" placeholder="Password">
        </div>
        <div class="row mb-3">
          <p class="h6 ps-0 pb-0"><i>Confirmation password:</i></p>
          <input class="ps-1 h5" type="password" id="confiramtionPassword" placeholder="Password">
        </div>
        <div class="row">
          <button type="button" class="btn btn-secondary">Sign Up</button>
        </div>
        <div class="row">
          <small class="p-0" style="color: grey;">
            Password must be 8-20 characters long, contain letters
            and numbers, and must not contain spaces nor special characters.
          </small>
        </div>
      </div>
      <div class="col-4 main"></div>
    </div>
  </div>
</template>

<script>
import { MazPhoneNumberInput } from "maz-ui";
import AddressLookup from "@/components/UnregisteredUser/components/AddressLookup";

export default {
  name: "RegistrationPage",
  components: {
    AddressLookup,
    MazPhoneNumberInput
  },
  data() {
    return {
      phoneNumber: null,
      results: null,
      query: "",
      position: null,
      quality: null,
    }
  },
  methods: {
    validate: function() {
      this.$refs.validator.validate(this.query).then(result => {
        this.quality = result.MatchQuality;
        this.position = result.Location.DisplayPosition;
      }, error => {
        console.error(error);
      });
    }
  }
}
</script>

<style scoped>
  input[type='text'], input[type='email'] {
    width: 100%;
    font-weight: 100;
  }

  input::placeholder {
    color: grey;
    font-weight: 100;
  }
</style>