 const axios = require("axios");

axios
  .post(
    "https://bfhldevapigw.healthrx.co.in/bfl-api-challenge/challenge-final",
    {
      "b_email": "bmlraGlsaGFya2FsOEBnbWFpbC5jb20=",
            "b_name": "SGFya2FsIE5pa2hpbCBCYWJhc2FoZWI=",
            "b_reg_no": "UEQwOTk5",
            "email": "nikhilharkal8@gmail.com",
            "name": "Harkal Nikhil Babasaheb",
            "personal_code": "GRAPHQL",
            "reg_no": "PD0999"
    }
  )
  .then((response) => {
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });