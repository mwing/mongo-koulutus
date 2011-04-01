require 'rubygems'
require 'sinatra'
require File.join(File.dirname(__FILE__), "documents.rb")
MongoMapper.database = "3sq"

class MongoRestApp < Sinatra::Base
  @@venues = [{:venue => "Logalialainen", :loc => [51,50]}, {:venue => "Gastone", :loc => [50,51]}]
  @@users = [{:name => "matti", :friends => ["pekka", "jaska"]}, {:name => "pekka", :friends => ["jaska"]}, {:name => "jaska", :friends => ["pekka"]}]
  get "/venues/list" do
#    @@venues.to_json
    Venue.all({:fields => ["name"]}).to_json
  end

# get for testing time
  get "/venues/search/:term" do |search_term|
#    @@venues.select{|venue| venue[:venue] =~ /#{search_term}/i }.to_json
    Venue.where(:name => /#{params[:term]}/i).all.to_json
  end

  get "/venues/near/:lat/:long" do |lat, long|
    "{}"
#    Venue.where(:location => {"$maxDistance" => 1, "$near" => [long.to_f, lat.to_f]}).all().to_json
    hash = BSON::OrderedHash.new()
    hash["$near"] = [lat.to_f, long.to_f]
    hash["$maxDistance"] = 1
    q = BSON::OrderedHash.new().merge!({:location => hash})
    Venue.where(q).all.to_json
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
		Venue.where
  end

end
