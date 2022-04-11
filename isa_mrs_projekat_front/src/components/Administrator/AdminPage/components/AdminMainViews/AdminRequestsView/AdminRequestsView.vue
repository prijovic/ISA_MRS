<template>
  <div v-if="requests">
    <div class="row main-col align-items-end">
      <div class="col-md-3 main-col">
        <div class="form-check form-check-inline">
          <label class="custom-control custom-checkbox">
            <input type="checkbox" id="checkbox1" class="custom-control-input" value="sign-up">
            <span class="custom-control-indicator"></span>
            <span class="custom-control-label">Sign Up Requests</span>
          </label>
        </div>
      </div>
      <div class="col-md-6 main-col">
        <div class="pl-2" style="display: flex">
          <label class="custom-control custom-checkbox">
            <input type="checkbox" id="checkbox2" class="custom-control-input" value="acc-del">
            <span class="custom-control-indicator"></span>
            <span class="custom-control-label">Account Deletion Requests</span>
          </label>
        </div>
      </div>
      <div class="col-md-3 main-col">
        <button class="btn btn-default"><font-awesome-icon class="icon" icon="paper-plane"/>My Responses</button>
      </div>
    </div>
    <div v-for="request in requests" v-bind:key="request">
      <div>
        <AdminRequestPreview :request="request"></AdminRequestPreview>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import AdminRequestPreview
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestPreview/AdminRequestPreview";
import axios from "axios/index";

library.add(faPaperPlane);

export default {
  name: "AdminRequestsView",
  components: {
    FontAwesomeIcon,
    AdminRequestPreview
  },
  async data() {
    return {
      requests: null
    }
  },
  async setup() {
    this.requests = await axios.get("/Requests/requests", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      },
      params: {
        offset: 0,
        pageSize: 10,
        field: "timeStamp"
      }
    });
    console.log(this.requests);
  }
}
</script>

<style scoped>
  .container-fluid {
    padding-right:0;
    padding-left:0;
    margin-right:0;
    margin-left:0;
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

  /*[class*="main-col"] {*/
  /*  background-color: aquamarine;*/
  /*  border: 2px solid darkblue;*/
  /*}*/
</style>