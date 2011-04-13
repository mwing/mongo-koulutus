require 'rubygems'
require 'sinatra'
require File.join(File.dirname(__FILE__), "documents.rb")

MongoMapper.database = "3sq"

class MongoRestApp < Sinatra::Base

  before {
    content_type "text/plain", :charset => "UTF-8"
  }

  get "/venues/list" do
    Venue.fields(:name).all.to_json
  end

  get "/venues/search/:term" do |search_term|
    Venue.where(:name => /#{params[:term]}/i).all.to_json
  end

  get "/venues/near/:lat/:long" do |lat, long|
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
    User.all.to_json
  end

  get "/friends/:user/list" do |user|
    friends = (u = User.first(:name => user) and u.friends) || []
    friends.to_json
  end

  get "/venue/tips/:venue" do |venue|
    venue
  end

  post "/venue/add_tip/:venue/" do |venue|
    Venue.where
  end

    get "/" do
    <<INDEX
<html>
    <head>
        <title>MongoDB</title>
    </head> <body>
        <h4>Urls</h4>
        <ul>
            <li><a href="/users/list">/users/list</a> - List all users</li>
            <li><a href="/venues/list">/venues/list</a> - List all venues</li>
            <li><a href="/venues/search/Zorbas">/venues/search/(part of venue name)</a> - Search for venue matching the term</li>
            <li><a href="/venues/near/24/60">/venues/near/(latitude)/(longtitude)</a> - Search for venues near the provided coordinates</li>
        </ul>
    </body>
</html>
INDEX
    end
end
