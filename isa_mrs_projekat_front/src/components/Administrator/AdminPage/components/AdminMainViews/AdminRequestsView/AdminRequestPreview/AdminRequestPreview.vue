<template>
  <div class="container-fluid rounded p-2 mb-3">
    <div class="row main-col align-items-end">
      <div class="col-md-8 main-col">
        <h5>{{fullName + " " + role}}</h5>
      </div>
      <div class="col-md-2 main-col">
        <button class="btn rounded-circle yes" @click.prevent="setActionYes" data-bs-toggle="modal" data-bs-target="#confirmationDialog">
          <font-awesome-icon icon="check" style="color: forestgreen"></font-awesome-icon>
        </button>
      </div>
      <div class="col-md-2 main-col">
        <div class="dropdown d-flex justify-content-start">
          <button class="btn rounded-circle dropdown no" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
            <font-awesome-icon icon="ban" style="color: darkred"></font-awesome-icon>
          </button>
          <div class="dropdown-menu"  style="width: 300px; height: 230px; background-color: #f7f7f2; border: 1px solid #3f5b25">
            <div class="container-fluid" style="background-color: #f7f7f2">
              <div class="row">
                <p>Please enter the reason for rejection below (50-200 characters):</p>
              </div>
              <div class="row">
                <form>
                  <div class="form-group">
                    <textarea class="form-control" style="resize: none" rows="3" maxlength="200" v-model="explanation"></textarea>
                  </div>
                  <div class="form-group">
                    <button class="btn btn-default" :class="explanationIsEntered?null:'disabled'" @click.prevent="setActionNo" data-bs-toggle="modal" data-bs-target="#confirmationDialog">Confirm</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
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
  <div class="modal fade" id="confirmationDialog" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Request Management Confirmation</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          Do you want to change request's status into {{action.toLowerCase()}} for the user {{fullName}}?
          <div v-if="action==='Rejected'">{{explanation}}</div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
          <button type="button" class="btn btn-success" @click="createResponse" data-bs-dismiss="modal">Yes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCheck, faBan } from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";
import store from "@/store";

library.add(faCheck, faBan);

export default {
  name: "AdminRequestPreview",
  props: ["request"],
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      explanation: "",
      action: ""
    }
  },
  methods:{
    setActionNo() {
      this.action = "Rejected";
    },
    setActionYes() {
      this.action = "Accepted";
    },
    createResponse() {
      axios.put("/Requests/request", null, {
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
        params: {
          id: this.request.id,
          status: this.action,
          reason: this.explanation,
          adminEmail: this.adminEmail
        }
      })
      .then((response)=> {
        const message = "Request " + response.data.id + " was successfully " + response.data.status.toLowerCase() + ".";
        this.$notify({
          title: "Request Management Notification",
          text: message,
          type: "success"
        });
        this.$emit("requestManaged");
      })
      .catch((error) =>{
        if (error.response.status===404) {
          this.$notify({
            title: "Request Not Found",
            text: "Request with the specified id does not exist!" ,
            type: "warn"
          })
        } else if (error.response.status===400) {
          this.$notify({
            title: "Invalid Request Status",
            text: "Request status does not have the right form!",
            type: "warn"
          })
        } else if (error.response.status===500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            type: "error"
          })
        }
      })
    }
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
    },
    explanationIsEntered() {
      return this.explanation.length > 50 && this.explanation.length < 200;
    },
    accessToken() {
      return store.state.access_token;
    },
    adminEmail() {
      return store.state.email;
    }
  }
}
</script>

<style scoped>
  .container-fluid {
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

  .btn-default {
    display: flex;
    align-items: center;

    cursor: pointer;
    position: relative;
    font-weight: 400;
    user-select: none;

    margin: 0.1em 0;
    padding: 0.2em;
    border-radius: 0.25em;
    height: 2em;

    color: #f7f7f2;
    background-color: #3F9725;
    text-decoration: none;
  }

  .btn-default:hover, .btn-default:active {
    color: #378220;
    background-color: #f7f7f2;
  }

  .btn-default .icon {
    flex-shrink: 0;
    margin-right: 10px;
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