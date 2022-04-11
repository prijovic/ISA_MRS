<template>
  <div class="container container-fluid rounded p-2 mb-3">
    <div class="row main-col align-items-end">
      <div class="col-md-8 main-col">
        <h5>{{fullName + " " + role}}</h5>
      </div>
      <div class="col-md-2 main-col">
        <button class="btn rounded-circle yes">
          <font-awesome-icon icon="check" style="color: forestgreen"></font-awesome-icon>
        </button>
      </div>
      <div class="col-md-2 main-col">
        <button type="button" class="btn rounded-circle no">
          <font-awesome-icon icon="ban" style="color: darkred"></font-awesome-icon>
        </button>
      </div>
    </div>
    <div class="row main-col align-items-end">
      <div class="col-md-12 main-col">
        <p class="h-4" style="color: gray">{{date}}</p>
      </div>
    </div>
    <div class="row main-col align-items-end">
      <div class="col-md-12 main-col">
        <div class="lineTitle">
          <p class="h4">{{title}}</p>
          <hr class="ms-1">
        </div>
      </div>
    </div>
    <div class="row main-col align-items-end">
      <div class="col-md-12 main-col">
        <div class="rounded" style="background-color: white">
          <p style="color: gray">
            {{this.request.reason}}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCheck, faBan } from "@fortawesome/free-solid-svg-icons";

library.add(faCheck, faBan);

export default {
  name: "AdminRequestPreview",
  props: ["request"],
  components: {
    FontAwesomeIcon
  },
  computed: {
    fullName() {
      return this.request.user.name + " " + this.request.user.surname;
    },
    role() {
      if (this.request.user.userType === "Admin") {
        return "(Admin)";
      } else if (this.request.user.userType === "SuperAdmin") {
        return "(Supreme Admin)";
      } else if (this.request.user.userType === "Instructor") {
        return "(Fishing Instructor)";
      } else if (this.request.user.userType === "VacationRentalOwner") {
        return "(Vacation House Owner)";
      } else if (this.request.user.userType === "BoatOwner") {
        return "(Boat Owner)";
      } else {
        return "(Client)";
      }
    },
    title() {
      if (this.request.type === "SignUp") {
        return "Sign Up Request";
      } else {
        return "Account Deletion Request";
      }
    },
    date() {
      let data = this.request.timeStamp.split("T");
      let date = null;
      let first_part = data[0].split("-");
      let second_part = data[1].split(":");
      date = first_part[2].concat(".").concat(first_part[1]).concat(".").concat(first_part[0]).concat(".").concat(" at ").concat(second_part[0]).concat(":").concat(second_part[1]);
      return date;
    }
  }
}
</script>

<style scoped>
  .container {
    background-color: #f7f7f2;
  }

  .lineTitle {
    display: flex;
  }

  hr {
    flex-grow: 1;
  }

  div.lineTitle {
    display: flex;
  }

  .btn {
    background-color: #f7f7f2;
    border: none;
  }

  .btn.yes:active, .btn.yes:hover, .btn.yes:focus {
    outline: none;
    background-color: greenyellow;
  }

  .btn.no:active, .btn.no:hover, .btn.no:focus {
    outline: none;
    background-color: red;
  }
</style>