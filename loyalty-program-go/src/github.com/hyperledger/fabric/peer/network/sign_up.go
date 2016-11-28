/*
Copyright IBM Corp. 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package network

import (
	"errors"
	"fmt"

	"github.com/hyperledger/fabric/core/peer"
	pb "github.com/hyperledger/fabric/protos"
	"github.com/spf13/cobra"
	"golang.org/x/net/context"
)

func signUpCmd() *cobra.Command {
	return networkSignUpCmd
}

var networkSignUpCmd = &cobra.Command{
	Use:   "signup <userId> <password><role><institution>",
	Short: "SignUp a new user to CLI.",
	Long:  `SignUp a new user. Must supply userId,password,role,institution.`,
	RunE: func(cmd *cobra.Command, args []string) error {
		return networkSignUp(args)
	},
}

// step one: check if userId,password,role,institution is valid
// step two: enroll these in the Devops server
func networkSignUp(args []string) error {
	logger.Info("CLI client sign up...")

	// Check for arguments:userId,password,role,institution
	if len(args) != 4 {
		return errors.New("Must supply userId,password,role and institution")
	}

	// Get a devopsClient to perform the login
	clientConn, err := peer.NewPeerClientConnection()
	if err != nil {
		return fmt.Errorf("Error trying to connect to local peer: %s", err)
	}
	devopsClient := pb.NewDevopsClient(clientConn)
	logger.Info("A connection has been established.....")

	//pb.NewDevopsClient(clientConn)
	// build the signUp spec and sign up
	signUpSpec := &pb.UserObject{UserId: args[0], Password: args[1], Role: args[2], Institution: args[3]}
	signUpResult, err := devopsClient.SignUp(context.Background(), signUpSpec)

	logger.Info("Call server method has finished ...")

	// Check if signUp is successful
	if signUpResult.Status == pb.Response_SUCCESS {
		logger.Info("Sign up is successful")
	} else {
		return fmt.Errorf("Error on client sign up: %s", string(signUpResult.Msg))
	}

	tok := string(signUpResult.Msg[:])
	fmt.Println("Your tok(password) is: " + tok)

	logger.Info("Command handling is done................")
	return nil
}
