require 'rubygems'
require 'sinatra'

class MongoRestApp < Sinatra::Base
  @@venues = [{:venue => "Logalialainen", :loc => [51,50]}, {:venue => "Gastone", :loc => [50,51]}]
  @@users = [{:name => "matti", :friends => ["pekka", "jaska"]}, {:name => "pekka", :friends => ["jaska"]}, {:name => "jaska", :friends => ["pekka"]}]
  get "/venues/list" do
    @@venues.to_json
  end

  get "/venues/search/:term" do |search_term|
    @@venues.select{|venue| venue[:venue] =~ /#{search_term}/i }.to_json
  end

  get "/venues/near/:lat/:long" do |lat, long|
    "{}"
  end

  post "/checkin/user/:venue" do |venue|
    "{}"
  end

  get "/friends/near/:lat/:long" do |lat, long|
    "{}"
  end

  get "/users/list" do
    "{}"
  end

  get "/friends/list/:user" do |user|
    "{}"
  end

  get "/venue/tips/:venue" do |venue|
    venue
  end

  post "/venue/add_tip/:venue/" do |venue|
  end

end
