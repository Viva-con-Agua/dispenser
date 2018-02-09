import $ from 'jquery';
import React, { Component } from "react";

export default class navigationAdd extends React.Component {
			
				constructor(props) {
								super(props);
								this.state = {
												name:'',
												entrys:['']
								};
								this.handleNameChange = this.handleNameChange.bind(this);
								this.handleSubmit = this.handleSubmit.bind(this);
				}

				handleNameChange(e) {
								this.setState({name: e.target.value});
				}

				handleSubmit(e) {
								e.preventDefault();
				}
				


				render() {
								return(
												<div className='NavigationAdd'>
													<form>
														<lable>
															Navigation:
															<input
																type="text"
																name="NavigationAddNameInput"
																onChange={this.handleNameChange}
															/>
														</lable>
															<input type="submit" value="Submit" />
													</form>
												</div>
												
								);
				}
}
