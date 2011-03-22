require 'rubygems'
require 'sinatra'

class MongoRestApp < Sinatra::Base

  get "/venues/list" do
    {:venues => [{:venue => "Logalialainen", :loc => [51,50]}, {:venue => "Gastone", :loc => [50,51]}]}.to_json
  end

  post "/venues/search/:term" do
  end

  get "/venues/near/:lat/:long" do
  end

  post "/checkin/user/:venue" do 
  end

  get "/friends/near/:lat/:long" do
  end

  get "/users/list" do
  end

  get "/friends/list/:user" do
  end

  get "/venue/tips/:venue" do
  end

  post "/venue/add_tip/:venue/" do
  end

end
