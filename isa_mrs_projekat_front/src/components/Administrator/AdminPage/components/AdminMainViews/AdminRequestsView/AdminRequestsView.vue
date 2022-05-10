<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <div class="container-fluid">
        <div class="align-items-center">
          <div class="row  main-col align-items-end">
            <div class="col-md-3 main-col">
              <div class="form-check form-check-inline">
                <label class="custom-control custom-checkbox">
                  <input type="checkbox" id="checkbox1" class="custom-control-input" v-model="requestTypes" value="SignUp" @change="filterRequests">
                  <span class="custom-control-indicator"></span>
                  <span class="custom-control-label">Sign Up Requests</span>
                </label>
              </div>
            </div>
            <div class="col-md-6 main-col">
              <div class="pl-2" style="display: flex">
                <label class="custom-control custom-checkbox">
                  <input type="checkbox" id="checkbox2" class="custom-control-input" v-model="requestTypes" value="AccountDeletion" @change="filterRequests">
                  <span class="custom-control-indicator"></span>
                  <span class="custom-control-label">Account Deletion Requests</span>
                </label>
              </div>
            </div>
          </div>
          <!-- Reviews listing -->
          <AdminRequestPreview v-for="(request, index) in this.requests" v-on:requestManaged="filterRequests()" :key="index" :request="request"/>
          <!-- Pagination -->
          <ul class="pagination justify-content-center">
            <li class="page-item" v-if="numberOfPages>2 && currentPage>1">
              <button class="page-link" @click="filterRequests">
                <font-awesome-icon icon="angle-double-left"/>
              </button>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage>0">
              <button class="page-link" @click="prevPage">
                <font-awesome-icon icon="angle-left"/>
              </button>
            </li>
            <li class="page-item">
              <h4 v-if="!requests" class="no-result"> NO RESULTS!<font-awesome-icon icon="frown"/></h4>
              <h4 v-else-if="numberOfPages!==1">{{currentPage+1}}/{{numberOfPages}}</h4>
              <h4 v-else>{{currentPage+1}}</h4>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage+1!==numberOfPages">
              <button class="page-link" @click="nextPage">
                <font-awesome-icon icon="angle-right"/>
              </button>
            </li>
            <li class="page-item" v-if="numberOfPages>0 && currentPage<numberOfPages-2">
              <button class="page-link" @click="lastPage">
                <font-awesome-icon icon="angle-double-right"/>
              </button>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faAngleRight, faAngleDoubleRight, faAngleLeft, faAngleDoubleLeft, faFrown } from "@fortawesome/free-solid-svg-icons";
import AdminRequestPreview
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminRequestsView/AdminRequestPreview/AdminRequestPreview";
import axios from "axios/index";
import { useStore } from "vuex"

library.add(faAngleRight, faAngleLeft, faFrown, faAngleDoubleRight, faAngleDoubleLeft);

export default {
  name: "AdminRequestsView",
  components: {
    FontAwesomeIcon,
    AdminRequestPreview
  },
  data() {
    return {
      requests: null,
      numberOfPages: null,
      currentPage: 0,
      pageSize: 30,
      requestTypes: [],
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Requests/requests", {headers: {
        Authorization: "Bearer " + store.state.access_token,
      },
      params: {
        page: this.currentPage,
        pageSize: this.pageSize,
        field: "timeStamp",
        types: "all"
      }},
    ).then(response => {
      this.requests = response.data.content;
      this.numberOfPages = response.data.pages;
    });
  },
  methods: {
    filterRequests() {
      let requestType = this.getFilter;
      this.currentPage = 0;
      axios.get("/Requests/requests", {
            headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            },
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,
              field: "timeStamp",
              types: requestType
            }
          },
      ).then(response => {
        this.requests = response.data.content;
        this.numberOfPages = response.data.pages;
      });
    },
    nextPage() {
      let requestType = this.getFilter;
      this.currentPage += 1;
      axios.get("/Requests/requests", {
            headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            },
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,
              field: "timeStamp",
              types: requestType
            }
          },
      ).then(response => {
        this.requests = response.data.content;
        this.numberOfPages = response.data.pages;
      });
    },
    prevPage() {
      let requestType = this.getFilter;
      this.currentPage -= 1;
      axios.get("/Requests/requests", {
            headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            },
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,
              field: "timeStamp",
              types: requestType
            }
          },
      ).then(response => {
        this.requests = response.data.content;
        this.numberOfPages = response.data.pages;
      });
    },
    lastPage() {
      let requestType = this.getFilter;
      this.currentPage = this.numberOfPages;
      axios.get("/Requests/requests", {
            headers: {
              Authorization: "Bearer " + this.$store.state.access_token,
            },
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,
              field: "timeStamp",
              types: requestType
            }
          },
      ).then(response => {
        this.requests = response.data.content;
        this.numberOfPages = response.data.pages;
      });
    }
  },
  computed: {
    getFilter() {
      let requestType;
      if (this.requestTypes.length === 2 || this.requestTypes.length === 0) {
        requestType = "all";
      }
      else {
        requestType = this.requestTypes[0];
      }
      return requestType;
    }
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

  .page-link {
    color: #378220;
  }

  h4.no-result {
    color: red;
    align-self: center;
  }

  h4 {
    vertical-align: center;
    margin-right: 3px;
    margin-left: 3px;
    color: #378220
  }
</style>